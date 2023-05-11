package com.rf.link.application.service.impl;


import com.rf.link.application.service.LinkCountService;
import com.rf.link.models.LinkCountRepository;
import com.rf.link.models.generated.LinkCount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LinkCountServiceImpl implements LinkCountService {

    private final LinkCountRepository linkCountRepository;


    @Override
    public Integer getCountByShortUrlId(String shortUrlId) {
        // 计数 写入数据库
        Optional<LinkCount> optionalLinkCount = linkCountRepository.getByShortUrlId(shortUrlId);
        LinkCount linkCount;
        if (optionalLinkCount.isPresent()) {
            linkCount = optionalLinkCount.get();
            linkCount.setCount(linkCount.getCount() + 1);
            linkCountRepository.updateByPrimaryKey(linkCount);
        } else {
            linkCount = new LinkCount();
            linkCount.setShortUrlId(shortUrlId);
            linkCount.setCount(1);
            linkCountRepository.insert(linkCount);
        }
        return linkCount.getCount();
    }
}