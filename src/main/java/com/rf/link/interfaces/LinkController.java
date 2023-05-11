package com.rf.link.interfaces;

import com.github.seanyinx.wing.spring.common.Response;
import com.rf.link.application.service.LinkService;
import com.rf.link.conf.UrlDomainConfig;
import com.rf.link.models.LinkRequest;
import com.rf.link.models.generated.LinkEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/link/")
public class LinkController {

    @Autowired
    private LinkService linkService;
    @Autowired(required = false)
    private UrlDomainConfig urlDomainConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/save")
    @ResponseStatus(CREATED)
    public Response<String> addLinkEntity(@RequestBody LinkRequest request) {
        boolean checkRes = linkService.checkOriginalUrl(request.getOriginalUrl());
        if (!checkRes) {
            return Response.failed(400);
        }
        String shortUrl = linkService.getShortUrlByOriginalUrl(request.getOriginalUrl());
        if (StringUtils.isNotBlank(shortUrl)) {
            return Response.ok(shortUrl);
        }
        shortUrl = linkService.add(request.getOriginalUrl());
        if (StringUtils.isBlank(shortUrl)) {
            return Response.failed(400, "保存失败");
        }
        return Response.ok(shortUrl);
    }

    @GetMapping("/{id}")
    public Response<LinkEntity> getLinkEntity(@PathVariable long id) {
        System.out.println("------------------");
//        System.out.println(urlDomainConfig.getDomainName());
        redisTemplate.opsForValue().set("a-y", "b-z");
        System.out.println(redisTemplate.opsForValue().get("a-y"));
        System.out.println("------------------");
        return Response.ok(linkService.getById(id));
    }

    @GetMapping("")
    public Response<List<LinkEntity>> getAll(
            @Positive @RequestParam(value = "cursor", required = false, defaultValue = "0") long cursor,
            @Positive @RequestParam(value = "limit", required = false, defaultValue = "50") Long limit) {
        List<LinkEntity> entities = linkService.getAll(cursor, limit);
        if (entities.isEmpty() || entities.size() < limit) {
            return Response.ok(entities);
        }
        return Response.ok(entities, entities.get(entities.size() - 1).getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteLinkEntity(@PathVariable long id) {
        linkService.deleteById(id);
    }

    @GetMapping("/getByShortUrl/")
    public Response<String> getOriginalUrlByShortUrl(@RequestParam String shortUrl) {
        String originalUrl = linkService.getByShortUrl(shortUrl);
        return Response.ok(originalUrl);
    }

    @GetMapping("/getTopLinks/")
    public Response<List<Map<String, Object>>> getTopLinks(@RequestParam Integer n) {
        List<Map<String, Object>> res = linkService.getTopLinkInfo(n);
        return Response.ok(res);
    }

}
