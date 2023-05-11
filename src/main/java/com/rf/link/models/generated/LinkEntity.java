package com.rf.link.models.generated;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

public class LinkEntity implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String shortUrlId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String shortUrl;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String originalUrl;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getShortUrlId() {
        return shortUrlId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setShortUrlId(String shortUrlId) {
        this.shortUrlId = shortUrlId == null ? null : shortUrlId.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getShortUrl() {
        return shortUrl;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl == null ? null : shortUrl.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getOriginalUrl() {
        return originalUrl;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl == null ? null : originalUrl.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}