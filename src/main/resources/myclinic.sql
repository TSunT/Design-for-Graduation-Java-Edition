/*
 Navicat Premium Data Transfer

 Source Server         : MySQL8
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : myclinic

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 25/10/2020 22:48:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dep
-- ----------------------------
DROP TABLE IF EXISTS `dep`;
CREATE TABLE `dep` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '部门名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dep
-- ----------------------------
BEGIN;
INSERT INTO `dep` VALUES (1, '心内科');
INSERT INTO `dep` VALUES (2, '呼吸科');
INSERT INTO `dep` VALUES (3, '血液科');
INSERT INTO `dep` VALUES (4, '消化科');
INSERT INTO `dep` VALUES (5, '内分泌科');
INSERT INTO `dep` VALUES (6, '免疫科');
INSERT INTO `dep` VALUES (7, '眼科');
INSERT INTO `dep` VALUES (8, '耳鼻喉科');
INSERT INTO `dep` VALUES (9, '口腔科');
INSERT INTO `dep` VALUES (10, '皮肤科');
INSERT INTO `dep` VALUES (11, '外科');
INSERT INTO `dep` VALUES (12, '收费处');
INSERT INTO `dep` VALUES (13, '药物管理处');
INSERT INTO `dep` VALUES (14, '信息化处');
INSERT INTO `dep` VALUES (15, '其它');
COMMIT;

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `patientid` int(11) NOT NULL AUTO_INCREMENT COMMENT '病号',
  `patientidentity` char(18) NOT NULL COMMENT '病人身份证号',
  `patientname` varchar(20) NOT NULL,
  `patientgender` int(11) DEFAULT '0',
  `patienttel` char(11) NOT NULL,
  `allergy` varchar(100) DEFAULT NULL,
  `inputtime` date DEFAULT NULL,
  PRIMARY KEY (`patientid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='病人表';

-- ----------------------------
-- Records of patient
-- ----------------------------
BEGIN;
INSERT INTO `patient` VALUES (1, '320101198209076092', 'patient1', 0, '111111', 'xxx', NULL);
INSERT INTO `patient` VALUES (2, '320101198209078081', 'patient2', 0, '111111', 'xxxxx', NULL);
INSERT INTO `patient` VALUES (3, '320101198203082213', 'patient3', 0, '111111', 'xxxxxx', NULL);
INSERT INTO `patient` VALUES (4, '320101198405062139', 'patient4', 0, '121212', 'xxxxx', NULL);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rname` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'DOCTOR');
INSERT INTO `role` VALUES (3, 'PATIENT');
COMMIT;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staffid` int(11) NOT NULL,
  `staffname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` enum('主任医师','副主任医师','主治医师','医师','医士','管理员','其它') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `salary` int(10) unsigned NOT NULL,
  `staffgender` int(11) DEFAULT '0',
  `stafftel` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dep` int(11) DEFAULT NULL,
  `office` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`staffid`),
  KEY `staffid` (`staffid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of staff
-- ----------------------------
BEGIN;
INSERT INTO `staff` VALUES (1, 'staff1', '管理员', 10000, 0, '1111', 1, '1');
INSERT INTO `staff` VALUES (2, 'staff2', '主治医师', 10000, 0, '2222', 1, '1');
INSERT INTO `staff` VALUES (3, 'staff3', '医士', 10000, 0, '3333', 1, '1');
INSERT INTO `staff` VALUES (4, 'staff4', '管理员', 10000, 0, '', 1, '1');
INSERT INTO `staff` VALUES (6, 'staff6', '医师', 9800, 0, '13513255412', 2, 'xxxx');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `enable` int(11) DEFAULT NULL COMMENT '可否使用',
  `locked` int(10) unsigned DEFAULT NULL COMMENT '是否被锁\r\n',
  `lastloginaddr` char(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '最后登录IP',
  `logintimes` int(11) DEFAULT NULL COMMENT '登录次数',
  `sid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sid` (`sid`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `staff` (`staffid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'user1', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 32, 1);
INSERT INTO `user` VALUES (2, 'user2', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 1, 2);
INSERT INTO `user` VALUES (3, 'user3', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 1, 3);
INSERT INTO `user` VALUES (15, 'user6', '$2a$10$tU3bXmIwZo9FCZiunYb4OOGAXuWx9RFJlYrhWdE4z1PMb0mdplvKm', 1, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES (16, 'user4', '$2a$10$/A5iNGza0polFD5OyWWRbeebW87Tdd9Fayn.HGiRpPRgZakKpXQ16', 1, 0, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `rid` int(11) DEFAULT NULL COMMENT '角色id',
  KEY `uid` (`uid`) USING BTREE,
  KEY `rid` (`rid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 3);
INSERT INTO `user_role` VALUES (15, 2);
INSERT INTO `user_role` VALUES (16, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
