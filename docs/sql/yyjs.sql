/*
Navicat MySQL Data Transfer

Source Server         : 10.170.130.230
Source Server Version : 80020
Source Host           : 10.170.130.230:3306
Source Database       : yyjs

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-01-20 10:05:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answer_info
-- ----------------------------
DROP TABLE IF EXISTS `answer_info`;
CREATE TABLE `answer_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ques_id` int DEFAULT NULL,
  `content` blob,
  `xsd` float DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `click_num` int DEFAULT NULL COMMENT '点击量',
  `pri_img` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `file_path` varchar(100) DEFAULT NULL COMMENT '文档路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer_info
-- ----------------------------
INSERT INTO `answer_info` VALUES ('3', '34', 0xE5A79AE6988EE8BAABE9AB98EFBC9A0A323236636D0AE5A79AE6988E2859616F204D696E6729EFBC8CE794B7EFBC8CE6B189E6978FEFBC8CE697A0E5859AE6B4BEE4BABAE5A3ABEFBC8C31393830E5B9B439E69C883132E697A5E587BAE7949FE4BA8EE4B88AE6B5B7E5B882E5BE90E6B187E58CBAEFBC8CE7A596E7B18DE6B19FE88B8FE79C81E88B8FE5B79EE5B882E590B4E6B19FE58CBAE99C87E6B3BDE99587EFBC8CE5898DE4B8ADE59BBDE8818CE4B89AE7AFAEE79083E8BF90E58AA8E59198EFBC8CE58FB8E8818CE4B8ADE9948BEFBC8CE78EB0E4BBBBE4BA9AE6B4B2E7AFAEE79083E88194E59088E4BC9AE4B8BBE5B8ADE38082, '10', '2020-09-01 12:12:12', '34', '', '');
INSERT INTO `answer_info` VALUES ('4', '34', 0xE5A79AE6988EE7AE80E58E86E8AFB4E6988E0AE5A79AE6988EEFBC8CE587BAE7949FE4BA8EE4B88AE6B5B7E280A6E280A6, '9', '2020-09-12 13:00:00', '21', '', '1.docx');
INSERT INTO `answer_info` VALUES ('5', '35', '', '10', '2020-09-01 12:12:12', '34', '', '');
INSERT INTO `answer_info` VALUES ('6', '35', '', '9', '2020-09-12 13:00:00', '21', '', '');
INSERT INTO `answer_info` VALUES ('7', '36', '', '10', '2020-09-01 12:12:12', '34', '', '');
INSERT INTO `answer_info` VALUES ('8', '36', '', '9', '2020-09-12 13:00:00', '21', '', '');

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES ('/home/zhangshuailing/jar/tb_zy/uploadFile/file/', 'path');

-- ----------------------------
-- Table structure for ques_info
-- ----------------------------
DROP TABLE IF EXISTS `ques_info`;
CREATE TABLE `ques_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '问题名称',
  `key_words` varchar(500) DEFAULT NULL COMMENT '关键词，以中文逗号区分开',
  `related_keys` varchar(500) DEFAULT NULL,
  `orderby` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ques_info
-- ----------------------------
INSERT INTO `ques_info` VALUES ('34', '姚明的身高是多少厘米？', '身高，姚明', '叶莉，姚沁蕾', '1');
INSERT INTO `ques_info` VALUES ('35', '叶莉的身高是多少厘米？', '身高，叶莉', '姚明，姚沁蕾', '34');
INSERT INTO `ques_info` VALUES ('36', '姚沁蕾的身高是多少厘米？', '身高，姚沁蕾', '姚明，叶莉', '35');

-- ----------------------------
-- Table structure for rec_info
-- ----------------------------
DROP TABLE IF EXISTS `rec_info`;
CREATE TABLE `rec_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '推荐信息',
  `orderby` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rec_info
-- ----------------------------
INSERT INTO `rec_info` VALUES ('1', '姚明的身高是多少厘米？', '1');
INSERT INTO `rec_info` VALUES ('2', '叶莉的身高是多少厘米？', '2');
