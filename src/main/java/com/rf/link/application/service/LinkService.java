package com.rf.link.application.service;

import com.rf.link.models.generated.LinkEntity;

import java.util.List;
import java.util.Map;

public interface LinkService {

    String add(String foo);

    void updateById(long someId, String foo);

    LinkEntity getById(long someId);

    List<LinkEntity> getAll(long cursor, long limit);

    void deleteById(long someId);

    String add(LinkEntity linkEntity);

    boolean checkOriginalUrl(String originalUrl);

    LinkEntity getByOriginalUrl(String url);

    String  getShortUrlByOriginalUrl(String url);

    String getByShortUrl(String url);

    List<Map<String,Object>> getTopLinkInfo(int n);
}
