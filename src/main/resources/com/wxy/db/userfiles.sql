/*
 Navicat MySQL Data Transfer

 Source Server         : mac
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost
 Source Database       : userfiles

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : utf-8

 Date: 07/24/2020 22:49:10 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_files`
-- ----------------------------
DROP TABLE IF EXISTS `t_files`;
CREATE TABLE `t_files` (
  `id` int(8) NOT NULL,
  `old_file_name` varchar(100) DEFAULT NULL,
  `new_file_name` varchar(100) DEFAULT NULL,
  `ext` varchar(20) DEFAULT NULL,
  `path` varchar(150) DEFAULT NULL,
  `size` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `is_img` varchar(8) DEFAULT NULL,
  `down_count` int(6) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(8) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', 'admin', 'q'), ('2', 'wxy', 'q');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
