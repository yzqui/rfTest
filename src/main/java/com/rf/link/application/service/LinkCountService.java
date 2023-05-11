package com.rf.link.application.service;

import com.rf.link.models.generated.LinkCount;

public interface LinkCountService {

    Integer getCountByShortUrlId(String shortUrlId);

}
