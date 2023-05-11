-- CREATE TABLE IF NOT EXISTS `link`
-- (
--     `id`
--     BIGINT
--     UNSIGNED
--     NOT
--     NULL
--     AUTO_INCREMENT
--     COMMENT
--     '主键',
--     `short_url_id`
--     varchar
-- (
--     100
-- ) NOT NULL DEFAULT '' COMMENT '短链唯一 id',
--     `short_url` varchar
-- (
--     100
-- ) NOT NULL DEFAULT '' COMMENT '短链',
--     `original_url` varchar
-- (
--     200
-- ) NOT NULL DEFAULT '' COMMENT '原始链接',
--     `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--     PRIMARY KEY
-- (
--     `id`
-- ),
--     UNIQUE KEY uk_short_url_id
-- (
--     `short_url_id`
-- )
--     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# truncate table link;
# CREATE TABLE IF NOT EXISTS `link_count`
# (
#     `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
#     `short_url_id`
#                    varchar(100)    NOT NULL DEFAULT '' COMMENT '短链唯一 id',
#     `count`        int             NOT NULL DEFAULT 0 COMMENT '查询次数',
#     `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
#     `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
#     PRIMARY KEY (`id`),
#     UNIQUE KEY uk_short_url_id (`short_url_id`)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8mb4;
