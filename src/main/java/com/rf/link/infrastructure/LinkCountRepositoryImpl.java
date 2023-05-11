
package com.rf.link.infrastructure;

import com.rf.link.models.LinkCountRepository;
import com.rf.link.models.generated.LinkCountMapper;
import com.rf.link.models.generated.LinkCount;
import lombok.AllArgsConstructor;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.Optional;

import static com.rf.link.models.generated.LinkCountDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

@AllArgsConstructor
public class LinkCountRepositoryImpl implements LinkCountRepository {

    private final LinkCountMapper mapper;

    @Override
    public int updateByPrimaryKey(LinkCount record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(LinkCount record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(LinkCount record) {
        return mapper.insert(record);
    }

    @Override
    public Optional<LinkCount> getByShortUrlId(String urlId) {
        SelectStatementProvider statementProvider = select(id, shortUrlId, count, createTime, updateTime)
                .from(linkCount)
                .where(shortUrlId, SqlBuilder.isEqualTo(urlId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return mapper.selectOne(statementProvider);
    }
}
