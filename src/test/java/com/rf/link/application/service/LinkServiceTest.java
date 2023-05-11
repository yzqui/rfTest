package com.rf.link.application.service;

import static com.seanyinx.github.unit.scaffolding.Randomness.nextId;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import com.github.seanyinx.wing.core.exceptions.InvalidRequestException;
import com.github.seanyinx.wing.core.UniqueIdGenerator;
import com.rf.link.application.service.impl.LinkServiceImpl;
import com.rf.link.conf.UrlDomainConfig;
import com.rf.link.models.LinkCountRepository;
import com.rf.link.models.generated.LinkEntity;
import com.rf.link.models.LinkRepository;

import java.net.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class LinkServiceTest {

    private final long id = nextId();
    private final LinkEntity entity = new LinkEntity();
    private final LinkRepository linkRepository = Mockito.mock(LinkRepository.class);
    private final LinkCountService linkCountService = Mockito.mock(LinkCountService.class);
    private final UniqueIdGenerator shotUrlGenerator = Mockito.mock(UniqueIdGenerator.class);
    private final UrlDomainConfig config = Mockito.mock(UrlDomainConfig.class);
    private final RedisTemplate<String, String> redisTemplate = Mockito.mock(RedisTemplate.class);
    private final LinkService service = new LinkServiceImpl(linkRepository, shotUrlGenerator, config,
            redisTemplate, linkCountService);

    private ValueOperations valueOperations = Mockito.mock(ValueOperations.class);
    private HashOperations hashOperations = Mockito.mock(HashOperations.class);
    private ZSetOperations zSetOperations = Mockito.mock(ZSetOperations.class);

    @Before
    public void setUp() {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        Mockito.when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        entity.setCreateTime(new Date());
    }

    @Test
    public void shouldAddOne() {
        service.add(entity);

        verify(linkRepository).insert(entity);
    }

    @Test
    public void shouldFindLinkEntity() {
        when(linkRepository.selectByPrimaryKey(id)).thenReturn(Optional.of(entity));

        LinkEntity link = service.getById(id);

        assertThat(link).isEqualTo(this.entity);
    }

    @Test(expected = InvalidRequestException.class)
    public void blowsUpIfNoSuchElement() {
        service.getById(id);
    }

    @Test
    public void shouldFindAll() {
        when(linkRepository.selectAll(0L, 50L)).thenReturn(singletonList(entity));

        List<LinkEntity> entities = service.getAll(0L, 50L);

        assertThat(entities).containsExactly(entity);
    }

    @Test
    public void shouldDeleteLinkEntity() {
        service.deleteById(id);

        verify(linkRepository).deleteByPrimaryKey(id);
    }

    @Test
    public void shouldNormalUrl() {
        String url = "https://goo.gl/xYz123";
        boolean result = service.checkOriginalUrl(url);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void shouldGetByOriginalUrl() {
        String url = "https://www.npr.org/2020/06/01/865510819/little-progress-made-in-trumps-plan-for-private-retailers-to-test-for-coronaviru";

        service.getByOriginalUrl(url);
        verify(linkRepository).selectByOriginalUrl(url);
    }

    @Test
    public void shouldGetByShortUrl() {
        String url = "http://short.url/7061680734230165441";
        when(linkRepository.getByShortUrl(url)).thenReturn(Optional.of(entity));

        String originalUrl = service.getByShortUrl(url);
        assertThat(originalUrl).isEqualTo(entity.getOriginalUrl());
    }

    @Test
    public void shouldGetTopNLinkInfo() {
        service.getTopLinkInfo(2);

        verify(redisTemplate.opsForZSet()).reverseRange("shortUrls", 0, 1);
    }
}
