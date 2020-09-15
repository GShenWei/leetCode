select *
from article;


/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.137-3306
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.0.137:3306
 Source Schema         : elecbusin

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 21/07/2020 16:58:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for elecbusin_coursecomment
-- ----------------------------
DROP TABLE IF EXISTS `elecbusin_coursecomment`;
CREATE TABLE `elecbusin_coursecomment`
(
    `idCourseComment`       bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '课程评论id',
    `idCourse`              bigint(20)                                              NULL DEFAULT NULL COMMENT '课程id',
    `idUser`                bigint(20)                                              NULL DEFAULT NULL COMMENT '用户id',
    `comment`               text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '课程评论',
    `creTime`               int(20)                                                 NULL DEFAULT NULL COMMENT '创建时间',
    `idCourseCommentTarget` bigint(20)                                              NULL DEFAULT 0 COMMENT '目标用户id',
    `score`                 double(10, 2)                                           NULL DEFAULT NULL COMMENT '分数',
    `userPic`               varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
    `userName`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
    PRIMARY KEY (`idCourseComment`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 36
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of elecbusin_coursecomment
-- ----------------------------
INSERT INTO `elecbusin_coursecomment`
VALUES (29, 123, 121, '1111', 1586742571, 0, 1.50, NULL, NULL);
INSERT INTO `elecbusin_coursecomment`
VALUES (30, 124, 121, '1111', 1586743082, 0, 1.50, NULL, NULL);
INSERT INTO `elecbusin_coursecomment`
VALUES (31, 124, 172, '测试评论', 1587431566, 29, 1.00, '7402291350156588615391.jpg', '一休哥路');
INSERT INTO `elecbusin_coursecomment`
VALUES (32, 119, 172, '咯咯咯', 1587432303, 31, 1.00, '7402291350156588615391.jpg', '一休哥路');
INSERT INTO `elecbusin_coursecomment`
VALUES (33, 108, 172, '好评', 1587432870, 0, 5.00, '7402291350156588615391.jpg', '一休哥路');
INSERT INTO `elecbusin_coursecomment`
VALUES (34, 126, 151, 'Same thing happened in my life ', 1587705486, 0, 5.00, '2682291915146114631393.jpg', 'Hank');
INSERT INTO `elecbusin_coursecomment`
VALUES (35, 166, 151, 'zzsdf', 1594282287, 0, 4.00, '2682291915146114631393.jpg', 'Hank');

SET FOREIGN_KEY_CHECKS = 1;



SELECT queryChildrenAreaInfo(29);

CREATE FUNCTION queryChildrenAreaInfo(areaId INT)
    RETURNS VARCHAR(4000)
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp = '$';
    SET sTempChd = CAST(areaId AS CHAR);

    WHILE sTempChd IS NOT NULL
        DO
            SET sTemp = CONCAT(sTemp, ',', sTempChd);
            SELECT GROUP_CONCAT(idCourseComment)
            into sTempChd
            FROM elecbusin_coursecomment
            WHERE FIND_IN_SET(idCourseCommentTarget, sTempChd) > 0;
        END WHILE;
    RETURN sTemp;
END;



DROP TABLE IF EXISTS `agent_application`;

DROP TABLE IF EXISTS `agent_application`;
CREATE TABLE `agent_application` (
                                     `id` int(10) unsigned NOT NULL AUTO_INCREMENT ,
                                     `application_id` int(10) NOT NULL DEFAULT '0' ,
                                     `application_name` varchar(50) NOT NULL DEFAULT '' ,
                                     `check_type` tinyint NOT NULL DEFAULT '0' ,
                                     `real_app_list` varchar(100) NOT NULL DEFAULT '' ,
                                     `operator` varchar(50) NOT NULL DEFAULT '' ,
                                     `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                     `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC ;

DROP TABLE IF EXISTS `agent_application_install_history`;
CREATE TABLE `agent_application_install_history` (
                                                     `id` int(10) unsigned NOT NULL AUTO_INCREMENT ,
                                                     `agent_id` int(10) NOT NULL DEFAULT '0' ,
                                                     `school_id` int(10) NOT NULL DEFAULT '0' ,
                                                     `application_id` int(10) NOT NULL DEFAULT '0' ,
                                                     `operator` varchar(50) NOT NULL DEFAULT '' ,
                                                     `succeed_app_list` varchar(100) NOT NULL DEFAULT '' ,
                                                     `failed_app_list` varchar(100) NOT NULL DEFAULT '' ,
                                                     `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                                     `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
                                                     PRIMARY KEY (`id`),
                                                     UNIQUE KEY `uk_org_agent_app`(`school_id`,`application_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC ;
alter table agent_application_install_history drop index nm_org_agent_app;
alter table agent_application_install_history add index uk_org_agent_app(`school_id`,`application_id`) ;



DROP TABLE IF EXISTS `agent_application_install_history`;

CREATE TABLE `agent_application_install_history`
(
    `id`             int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `agent_id`       int(10)          NOT NULL DEFAULT '0' COMMENT '代理商id',
    `org_id`         int(10)          NOT NULL DEFAULT '0' COMMENT '学校id',
    `application_id` int(10)          NOT NULL DEFAULT '0' COMMENT '应用id',
    `operator`       varchar(50)      NOT NULL DEFAULT '' COMMENT '操作者',
    `created_at`     timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     timestamp        NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_org_agent_app` (`org_id`, `agent_id`, `application_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='代理应用安装历史表';

select *
from agent_application aa
where not exists(
        select 1
        from agent_application_install_history aai
        where aai.agent_id = 0
          and aai.org_id = 0
          and aa.application_id = aai.application_id
    );
insert into agent_application(application_id, application_name, check_type)values (1, 'an App 1', 1),(2, 'an App 2', 1),(3, 'an App 3', 0);
insert into campus_department.agent_application (application_id, application_name, check_type)
select app_id,app_name,0 from open_platform.t_app_info where app_type=4;



