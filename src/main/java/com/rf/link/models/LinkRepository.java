package com.rf.link.models;

import com.rf.link.models.generated.LinkEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {

  List<LinkEntity> selectAll(long cursor, long limit);

  int deleteByPrimaryKey(Long id);

  Optional<LinkEntity> selectByPrimaryKey(Long id);

  int updateByPrimaryKey(LinkEntity record);

  int updateByPrimaryKeySelective(LinkEntity record);

  int insert(LinkEntity record);

  int insertMultiple(Collection<LinkEntity> records);

  int insertSelective(LinkEntity record);

  Optional<LinkEntity> selectByOriginalUrl(String url);

  Optional<LinkEntity> getByShortUrl(String s);
}
