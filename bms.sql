/*
Navicat MySQL Data Transfer

Source Server         : localMysql
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : bms

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-10-11 17:35:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(128) NOT NULL COMMENT '书名',
  `isbn` varchar(128) NOT NULL COMMENT '书的isbn,国际标准书号',
  `sex` varchar(2) DEFAULT NULL,
  `author` varchar(32) NOT NULL COMMENT '作者',
  `publish_company` varchar(128) NOT NULL COMMENT '出版公司',
  `publish_date` date NOT NULL COMMENT '出版日期',
  `description` varchar(255) DEFAULT NULL COMMENT '书的简述',
  `storage_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '入库日期',
  `storage` int(11) NOT NULL DEFAULT '1' COMMENT '库存总量',
  `loan` int(11) NOT NULL DEFAULT '0' COMMENT '借出',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '书的单价',
  `booktype_id` int(11) NOT NULL DEFAULT '0' COMMENT '书的类别0其他',
  `cover` varchar(50) NOT NULL COMMENT '书籍封面,对应related_file表的local_name',
  `loan_time` int(11) DEFAULT '0' COMMENT '借阅次数',
  PRIMARY KEY (`book_id`,`book_name`,`author`),
  KEY `fk_book_booktypeid_1` (`booktype_id`),
  CONSTRAINT `fk_book_booktypeid_1` FOREIGN KEY (`booktype_id`) REFERENCES `tb_booktype` (`booktype_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_book
-- ----------------------------

-- ----------------------------
-- Table structure for tb_booktype
-- ----------------------------
DROP TABLE IF EXISTS `tb_booktype`;
CREATE TABLE `tb_booktype` (
  `booktype_id` int(11) NOT NULL AUTO_INCREMENT,
  `booktype_name` varchar(32) NOT NULL,
  `booktype_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`booktype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_booktype
-- ----------------------------

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL COMMENT '书编号',
  `user_id` int(11) NOT NULL COMMENT '评论用户编号',
  `content` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  `pictures` varchar(2000) DEFAULT '' COMMENT '评论图片,对应ti_related_file的local_name字段,多张用逗号隔开',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`comment_id`,`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_record`;
CREATE TABLE `tb_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `loantime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `loan_days` int(11) NOT NULL DEFAULT '30' COMMENT '借阅天数',
  `returntime` datetime DEFAULT NULL COMMENT '实际归还时间',
  `is_over` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否借阅超期,0否1是,由定时任务完成',
  PRIMARY KEY (`record_id`,`book_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_record
-- ----------------------------

-- ----------------------------
-- Table structure for ti_button
-- ----------------------------
DROP TABLE IF EXISTS `ti_button`;
CREATE TABLE `ti_button` (
  `button_id` int(11) NOT NULL AUTO_INCREMENT,
  `button_name` varchar(32) NOT NULL,
  `menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`button_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单按键表';

-- ----------------------------
-- Records of ti_button
-- ----------------------------

-- ----------------------------
-- Table structure for ti_depart
-- ----------------------------
DROP TABLE IF EXISTS `ti_depart`;
CREATE TABLE `ti_depart` (
  `depart_id` int(11) NOT NULL,
  `depart_name` varchar(32) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`depart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of ti_depart
-- ----------------------------

-- ----------------------------
-- Table structure for ti_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `ti_dictionary`;
CREATE TABLE `ti_dictionary` (
  `dic_id` int(11) NOT NULL,
  `dic_name` varchar(32) NOT NULL COMMENT '字典名',
  `dic_code` varchar(32) NOT NULL COMMENT '唯一标识符,不可重复',
  `parent_id` int(11) NOT NULL COMMENT '上级字典',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`dic_id`),
  UNIQUE KEY `index_diccode` (`dic_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of ti_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for ti_menu
-- ----------------------------
DROP TABLE IF EXISTS `ti_menu`;
CREATE TABLE `ti_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(32) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `menu_url` varchar(128) NOT NULL,
  `menu_icon` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单图标,必填,默认star.svg',
  `menu_i18n` varchar(32) NOT NULL COMMENT '菜单国际化字段,可做唯一标识',
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='菜单表,根菜单必须手动添加,不可通过程序添加';

-- ----------------------------
-- Records of ti_menu
-- ----------------------------
INSERT INTO `ti_menu` VALUES ('1', '菜单', '0', '/', 'star.svg', 'menu', '1');
INSERT INTO `ti_menu` VALUES ('2', '首页', '1', '/', 'star.svg', 'home', '2');
INSERT INTO `ti_menu` VALUES ('3', '系统设置', '2', '/system', 'star.svg', 'sysSetting', '3');
INSERT INTO `ti_menu` VALUES ('4', '用户设置', '3', '/system/user', 'star.svg', 'userSetting', '4');
INSERT INTO `ti_menu` VALUES ('5', '角色设置', '3', '/system/role', 'star.svg', 'roleSetting', '5');

-- ----------------------------
-- Table structure for ti_related_file
-- ----------------------------
DROP TABLE IF EXISTS `ti_related_file`;
CREATE TABLE `ti_related_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `local_name` varchar(64) NOT NULL COMMENT '存储在本地的名称,规则是yyyyMMdd_文件后缀_32uuid',
  `file_name` varchar(64) NOT NULL COMMENT '文件本来的名字',
  `file_type` tinyint(4) NOT NULL COMMENT '文件类型1图片2音频3视频4文本5其他',
  `file_size` decimal(5,2) DEFAULT NULL COMMENT '文件大小,单位M',
  `file_time` varchar(10) DEFAULT NULL COMMENT '音视频文件时长,格式为HH:mm:ss',
  `file_suffix` varchar(10) DEFAULT NULL COMMENT '文件后缀,不需要点',
  `uploadtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='文件表';

-- ----------------------------
-- Records of ti_related_file
-- ----------------------------
INSERT INTO `ti_related_file` VALUES ('11', '1ft8ufjckoh4bop2t8oobeg97u_20180926.jpg', 'test02', '1', '0.26', null, 'jpg', '2018-09-26 10:56:33');

-- ----------------------------
-- Table structure for ti_role
-- ----------------------------
DROP TABLE IF EXISTS `ti_role`;
CREATE TABLE `ti_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL,
  `role_state` tinyint(4) DEFAULT '1' COMMENT '角色状态0不可见,只有超级管理员不可见,1可见',
  `role_level` int(11) NOT NULL COMMENT '权限等级,当角色有多权限时,登录时以高权限展示菜单',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ti_role
-- ----------------------------
INSERT INTO `ti_role` VALUES ('1', '超级管理员', '0', '10000');
INSERT INTO `ti_role` VALUES ('2', '管理员', '1', '9999');

-- ----------------------------
-- Table structure for ti_user
-- ----------------------------
DROP TABLE IF EXISTS `ti_user`;
CREATE TABLE `ti_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码,md5加密',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `depart_id` int(11) DEFAULT NULL,
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别,男m,女f',
  `address` varchar(128) DEFAULT NULL COMMENT '家庭住址',
  `email` varchar(32) DEFAULT NULL COMMENT '邮件',
  `salary` decimal(10,2) DEFAULT NULL COMMENT '工资',
  `tel` varchar(32) DEFAULT NULL COMMENT '电话',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态,0黑名单1正常',
  `user_icon` varchar(128) DEFAULT NULL COMMENT '用户图标',
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`username`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of ti_user
-- ----------------------------
INSERT INTO `ti_user` VALUES ('1', 'admin', '123456', '飞花梦影', null, null, null, '18', 'm', null, null, null, null, '1', 'test01.jpg', '2018-09-12 16:57:17', '2018-09-29 16:33:57');

-- ----------------------------
-- Table structure for tr_role_button
-- ----------------------------
DROP TABLE IF EXISTS `tr_role_button`;
CREATE TABLE `tr_role_button` (
  `role_id` int(11) NOT NULL,
  `button_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`button_id`),
  KEY `fk_rolebuttion_buttonid` (`button_id`),
  CONSTRAINT `tr_role_button_ibfk_1` FOREIGN KEY (`button_id`) REFERENCES `ti_button` (`button_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tr_role_button_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `ti_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色按钮中间表';

-- ----------------------------
-- Records of tr_role_button
-- ----------------------------

-- ----------------------------
-- Table structure for tr_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tr_role_menu`;
CREATE TABLE `tr_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  KEY `fk_role_menu_role_id` (`role_id`),
  KEY `fk_role_menu_menu_id` (`menu_id`),
  CONSTRAINT `tr_role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `ti_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tr_role_menu_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `ti_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tr_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tr_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tr_user_role`;
CREATE TABLE `tr_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `index_user_role_role_id` (`role_id`) USING BTREE,
  CONSTRAINT `tr_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `ti_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tr_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ti_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

-- ----------------------------
-- Records of tr_user_role
-- ----------------------------
INSERT INTO `tr_user_role` VALUES ('1', '1');
