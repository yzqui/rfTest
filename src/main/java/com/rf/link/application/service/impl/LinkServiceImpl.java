package com.rf.link.application.service.impl;


import com.github.seanyinx.wing.core.exceptions.InvalidRequestException;
import com.github.seanyinx.wing.core.UniqueIdGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rf.link.application.service.LinkCountService;
import com.rf.link.application.service.LinkService;
import com.rf.link.conf.UrlDomainConfig;
import com.rf.link.models.generated.LinkEntity;
import com.rf.link.models.LinkRepository;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rf.link.models.SomeErrorCode.RESOURCE_NOT_FOUND;

@Service
@AllArgsConstructor
public class LinkServiceImpl implements LinkService {

    private static final String URL_PRE = "http://";
    public static final Integer REDIS_ZSET_MAX = 5;
    private static final String SHORT_URLS = "shortUrls";
    private static final String SHORT_URL_ID = "shortUrlId";

    private final LinkRepository linkRepository;
    private final UniqueIdGenerator shortIdGenerator;

    @Autowired(required = false)
    private UrlDomainConfig urlDomainConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private LinkCountService linkCountService;

    @Transactional
    @Override
    public String add(String originalUrl) {
        LinkEntity entity = new LinkEntity();
        entity.setOriginalUrl(originalUrl);
        return this.add(entity);
    }

    @Transactional
    @Override
    public void updateById(long id, String foo) {
        linkRepository.updateByPrimaryKey(new LinkEntity());
    }

    @Transactional
    @Override
    public LinkEntity getById(long id) {
        Optional<LinkEntity> entity = linkRepository.selectByPrimaryKey(id);
        if (!entity.isPresent()) {
            throw new InvalidRequestException(RESOURCE_NOT_FOUND, RESOURCE_NOT_FOUND.getCause(id));
        }
        return entity.get();
    }

    @Override
    public List<LinkEntity> getAll(long cursor, long limit) {
        return linkRepository.selectAll(cursor, limit);
    }

    @Override
    public void deleteById(long id) {
        linkRepository.deleteByPrimaryKey(id);
    }

    @Override
    public String add(LinkEntity linkEntity) {
        linkEntity.setShortUrlId(String.valueOf(shortIdGenerator.nextId()));
        String shortUrl = URL_PRE + urlDomainConfig.getDomainName() + "/" + linkEntity.getShortUrlId();
        linkEntity.setShortUrl(shortUrl);
        linkRepository.insert(linkEntity);
        return linkEntity.getShortUrl();
    }

    @Override
    public boolean checkOriginalUrl(String originalUrl) {
        return UrlValidator.getInstance().isValid(originalUrl);
    }

    @Override
    public LinkEntity getByOriginalUrl(String url) {
        return linkRepository.selectByOriginalUrl(url).orElse(null);
    }

    @Override
    public String getShortUrlByOriginalUrl(String url) {
        String shortUrl = redisTemplate.opsForValue().get(url);
        if (StringUtils.isNotBlank(shortUrl)) {
            return shortUrl;
        }
        Optional<LinkEntity> entity = linkRepository.selectByOriginalUrl(url);
        if (entity.isPresent()) {
            redisTemplate.opsForValue().set(url, entity.get().getShortUrl());
            return entity.get().getOriginalUrl();
        }
        return null;
    }

    @Override
    public String getByShortUrl(String url) {
        // 缓存中查找
        Map<Object, Object> map = redisTemplate.opsForHash().entries(url);
        if (!map.isEmpty() && StringUtils.isNotBlank((String) map.get("originalUrl"))) {
            // 异步计数入库+入缓存
            Map<Object, Object> finalMap = map;
            CompletableFuture.runAsync(() -> asyncDealRedisZSetData(finalMap));
            return (String) map.get("originalUrl");
        }
        // 缓存中未查到，从数据库中查找
        Optional<LinkEntity> optionalLink = linkRepository.getByShortUrl(url);
        if (!optionalLink.isPresent()) {
            return null;
        }
        LinkEntity linkEntity = optionalLink.get();
        // 写入缓存
        map = new HashMap<>();
        map.put("shortUrl", linkEntity.getShortUrl());
        map.put("shortUrlId", linkEntity.getShortUrlId());
        map.put("originalUrl", linkEntity.getOriginalUrl());
        map.put("linkCreateTime", DateUtils.formatDate(linkEntity.getCreateTime(), "yyyy-MM-dd"));
        redisTemplate.opsForHash().putAll(url, map);
        // 异步计数入库+入缓存
        Map<Object, Object> fMap = map;
        CompletableFuture.runAsync(() -> asyncDealRedisZSetData(fMap));

        return linkEntity.getOriginalUrl();
    }

    @Override
    public List<Map<String, Object>> getTopLinkInfo(int n) {
        List<String> list = new ArrayList<>(redisTemplate.opsForZSet().reverseRange(SHORT_URLS, 0, n - 1));

        List<Map<String, Object>> maps = new ArrayList<>();
        for (String str : list) {
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> map = new Gson().fromJson(str, type);
            Double score = redisTemplate.opsForZSet().score(SHORT_URLS, str);
            map.put("score", score);
            maps.add(map);
        }
        return maps;
    }

    private void asyncDealRedisZSetData(Map<Object, Object> linkMap) {
        String shortUrlId = (String) linkMap.get(SHORT_URL_ID);
        linkMap.remove(SHORT_URL_ID);
        Integer count = linkCountService.getCountByShortUrlId(shortUrlId);
        // 写入 redis
        if (Objects.isNull(redisTemplate.opsForZSet().size(SHORT_URLS))) {
            redisTemplate.opsForZSet().add(SHORT_URLS, new Gson().toJson(linkMap), count);
            return;
        }
        long shortUrlSize = redisTemplate.opsForZSet().size(SHORT_URLS);
        if (shortUrlSize < REDIS_ZSET_MAX) {
            redisTemplate.opsForZSet().add(SHORT_URLS, new Gson().toJson(linkMap), count);
        } else {
            List<String> values = new ArrayList<>(redisTemplate.opsForZSet().range(SHORT_URLS, 0, -1));
            if (values.contains(new Gson().toJson(linkMap))) {
                redisTemplate.opsForZSet().add(SHORT_URLS, new Gson().toJson(linkMap), count);
                return;
            }
            int score = redisTemplate.opsForZSet().score(SHORT_URLS, values.get(0)).intValue();
            if (score < count) {
                Long res = redisTemplate.opsForZSet().remove(SHORT_URLS, values.get(0));
                if (res != null && res > 0) {
                    redisTemplate.opsForZSet().add(SHORT_URLS, new Gson().toJson(linkMap), count);
                }
            }
        }
    }
}
