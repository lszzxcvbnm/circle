/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50087
Source Host           : localhost:3307
Source Database       : b411

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2021-07-16 19:34:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `answer_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `answer_data` varchar(255) NOT NULL,
  `answer_conter` varchar(255) NOT NULL,
  `selected` varchar(255) NOT NULL,
  `problem_id` varchar(255) NOT NULL,
  PRIMARY KEY  (`answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer
-- ----------------------------

-- ----------------------------
-- Table structure for circle
-- ----------------------------
DROP TABLE IF EXISTS `circle`;
CREATE TABLE `circle` (
  `circle_id` varchar(32) NOT NULL,
  `circle_name` varchar(255) NOT NULL,
  `circle_data` varchar(255) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `circle_Introduce` varchar(255) NOT NULL,
  `circle_icon` varchar(255) NOT NULL,
  PRIMARY KEY  (`circle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of circle
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `comment_data` varchar(255) NOT NULL,
  `comment_conter` varchar(255) NOT NULL,
  `parent_comment_id` varchar(255) NOT NULL,
  `dynamic_id` varchar(255) NOT NULL,
  PRIMARY KEY  (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic` (
  `dynamic_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `dynamic_data` varchar(255) NOT NULL,
  `dynamic_content` varchar(255) NOT NULL,
  `circle_id` varchar(32) NOT NULL,
  `likes` int(11) NOT NULL,
  `comments` int(11) NOT NULL,
  `type` int(1) NOT NULL,
  PRIMARY KEY  (`dynamic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dynamic
-- ----------------------------

-- ----------------------------
-- Table structure for dynamic_like
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_like`;
CREATE TABLE `dynamic_like` (
  `id` int(11) NOT NULL auto_increment,
  `liked_dynamic_id` varchar(32) NOT NULL COMMENT '被点赞的动态id',
  `liked_user_id` varchar(32) NOT NULL COMMENT '点赞的用户id',
  `status` tinyint(1) NOT NULL default '1' COMMENT '点赞状态，0取消，1点赞',
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY  (`id`),
  KEY `liked_user_id` USING BTREE (`liked_dynamic_id`),
  KEY `liked_post_id` USING BTREE (`liked_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户点赞表';

-- ----------------------------
-- Records of dynamic_like
-- ----------------------------

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `likes_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `comment_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`likes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of likes
-- ----------------------------

-- ----------------------------
-- Table structure for likes_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `likes_dynamic`;
CREATE TABLE `likes_dynamic` (
  `likes_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `dynamic_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`likes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of likes_dynamic
-- ----------------------------

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `photo_id` varchar(32) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `photo_string` varchar(255) NOT NULL,
  `dynamic_id` varchar(255) NOT NULL,
  `circle_id` varchar(255) NOT NULL,
  PRIMARY KEY  (`photo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of photo
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `data` varchar(255) NOT NULL,
  `is_admin` varchar(255) NOT NULL,
  `pass_word` varchar(255) NOT NULL,
  `personlcon` varchar(255) NOT NULL,
  `introduction` varchar(255) NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('17a33c2e355e441a8a61162dd60cfb85', 'zs', '2021-06-21 15:16:41', 'admin', '$2a$10$Dh6IYB1788VAZtPqP2XDq.HBGCgKJzd8rWq3HC1Xghu7o7uG/q9W6', 'lsIcon', '这是管理员账号密码111111');
INSERT INTO `user` VALUES ('79dd9d5ff64147b88b58bd788c963ab1', 'wu', '2021-06-21 16:08:26', 'admin', '$2a$10$Dh6IYB1788VAZtPqP2XDq.HBGCgKJzd8rWq3HC1Xghu7o7uG/q9W6', 'perlocn', '这是管理员账号密码111111');

-- ----------------------------
-- Table structure for user_circle
-- ----------------------------
DROP TABLE IF EXISTS `user_circle`;
CREATE TABLE `user_circle` (
  `user_circle_id` varchar(32) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `circle_id` varchar(255) NOT NULL,
  PRIMARY KEY  (`user_circle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_circle
-- ----------------------------
