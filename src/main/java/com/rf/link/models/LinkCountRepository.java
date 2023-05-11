package com.rf.link.models;

import com.rf.link.models.generated.LinkCount;

import java.util.Optional;

public interface LinkCountRepository {

  int updateByPrimaryKey(LinkCount record);

  int updateByPrimaryKeySelective(LinkCount record);

  int insert(LinkCount record);

  Optional<LinkCount> getByShortUrlId(String s);
}
