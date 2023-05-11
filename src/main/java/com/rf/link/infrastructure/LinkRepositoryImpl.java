
package com.rf.link.infrastructure;

import static com.rf.link.models.generated.LinkEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isGreaterThan;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import com.rf.link.models.LinkRepository;
import com.rf.link.models.generated.LinkEntity;
import com.rf.link.models.generated.LinkEntityMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

@AllArgsConstructor
public class LinkRepositoryImpl implements LinkRepository {

    private final LinkEntityMapper mapper;

    @Override
    public List<LinkEntity> selectAll(long cursor, long limit) {
        SelectStatementProvider selectStatement = select(id, shortUrlId, shortUrl, originalUrl, createTime, updateTime)
                .from(linkEntity
                )
                .where(id, isGreaterThan(cursor))
                .limit(limit)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return mapper.selectMany(selectStatement);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public Optional<LinkEntity> selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(LinkEntity record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(LinkEntity record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(LinkEntity record) {
        return mapper.insert(record);
    }

    @Override
    public int insertMultiple(Collection<LinkEntity> records) {
        return mapper.insertMultiple(records);
    }

    @Override
    public int insertSelective(LinkEntity record) {
        return mapper.insertSelective(record);
    }

    @Override
    public Optional<LinkEntity> selectByOriginalUrl(String url) {
        SelectStatementProvider statementProvider = select(id, shortUrlId, shortUrl, originalUrl, createTime, updateTime)
                .from(linkEntity)
                .where(originalUrl, SqlBuilder.isEqualTo(url))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return mapper.selectOne(statementProvider);
    }

    @Override
    public Optional<LinkEntity> getByShortUrl(String url) {
        SelectStatementProvider statementProvider = select(id, shortUrlId, shortUrl, originalUrl, createTime, updateTime)
                .from(linkEntity)
                .where(shortUrl, SqlBuilder.isEqualTo(url))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return mapper.selectOne(statementProvider);
    }
}
