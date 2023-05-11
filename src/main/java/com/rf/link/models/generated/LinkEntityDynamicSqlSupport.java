package com.rf.link.models.generated;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class LinkEntityDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final LinkEntity linkEntity = new LinkEntity();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> id = linkEntity.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> shortUrlId = linkEntity.shortUrlId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> shortUrl = linkEntity.shortUrl;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> originalUrl = linkEntity.originalUrl;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = linkEntity.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> updateTime = linkEntity.updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class LinkEntity extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> shortUrlId = column("short_url_id", JDBCType.VARCHAR);

        public final SqlColumn<String> shortUrl = column("short_url", JDBCType.VARCHAR);

        public final SqlColumn<String> originalUrl = column("original_url", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public LinkEntity() {
            super("link");
        }
    }
}