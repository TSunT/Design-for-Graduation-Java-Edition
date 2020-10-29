/*
 Navicat Premium Data Transfer

 Source Server         : MySQL8_local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : myclinic

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 29/10/2020 17:56:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dep
-- ----------------------------
DROP TABLE IF EXISTS `dep`;
CREATE TABLE `dep`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '部门名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dep
-- ----------------------------
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

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `patientid` int(0) NOT NULL AUTO_INCREMENT COMMENT '病号',
  `patientidentity` char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '病人身份证号',
  `patientname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `patientgender` int(0) NULL DEFAULT 0,
  `patienttel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `allergy` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inputtime` date NULL DEFAULT NULL,
  PRIMARY KEY (`patientid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '病人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (1, '320101198209076092', 'patient1', 0, '111111', 'xxx', NULL);
INSERT INTO `patient` VALUES (2, '320101198209078081', 'patient2', 0, '111111', 'xxxxx', NULL);
INSERT INTO `patient` VALUES (3, '320101198203082213', 'patient3', 0, '13321554231', 'xxxxxx', NULL);
INSERT INTO `patient` VALUES (4, '320101198405062139', 'patient4', 0, '13912313224', 'xxxxx', NULL);
INSERT INTO `patient` VALUES (5, '320104199702045413', 'patient5', 0, '13321554231', '无', '2020-10-26');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `rid` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rname` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'DOCTOR');
INSERT INTO `role` VALUES (3, 'PATIENT');
INSERT INTO `role` VALUES (4, 'PHARMACY');
INSERT INTO `role` VALUES (5, 'NOTICE');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `staffid` int(0) NOT NULL,
  `staffname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary` int(0) UNSIGNED NOT NULL,
  `staffgender` int(0) NULL DEFAULT 0,
  `stafftel` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dep` int(0) NULL DEFAULT NULL,
  `office` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `staffid`(`staffid`) USING BTREE,
  INDEX `dep`(`dep`) USING BTREE,
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`dep`) REFERENCES `dep` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (1, 'staff1', NULL, 10000, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (2, 'staff2', '其它', 10000, 0, '1111', 1, '1111');
INSERT INTO `staff` VALUES (3, 'staff3', NULL, 10000, 0, NULL, 3, NULL);
INSERT INTO `staff` VALUES (4, 'staff4', NULL, 10000, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (5, 'staff5', '医士', 8000, 0, '13372009880', 3, 'office');
INSERT INTO `staff` VALUES (6, 'staff6', '医师', 10000, 0, '13372009881', 2, 'office');
INSERT INTO `staff` VALUES (7, 'notice', '其它', 5500, 1, '13513255414', 15, '1floor');

-- ----------------------------
-- Table structure for treatment
-- ----------------------------
DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment`  (
  `patientid` int(0) NOT NULL,
  `staffid` int(0) NOT NULL,
  `time` date NULL DEFAULT NULL,
  `heartrate` int(0) UNSIGNED NULL DEFAULT NULL,
  `bloodpressure` int(0) UNSIGNED NULL DEFAULT NULL,
  `temperature` int(0) UNSIGNED NULL DEFAULT NULL,
  `patientsymptoms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `presentillness` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `pastillness` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `diagnose` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `prescription` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `completed` tinyint(1) NOT NULL DEFAULT 0,
  INDEX `patientid`(`patientid`) USING BTREE,
  INDEX `staffid`(`staffid`) USING BTREE,
  CONSTRAINT `treatment_ibfk_1` FOREIGN KEY (`patientid`) REFERENCES `patient` (`patientid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `treatment_ibfk_2` FOREIGN KEY (`staffid`) REFERENCES `staff` (`staffid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of treatment
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `enable` int(0) NULL DEFAULT NULL COMMENT '可否使用',
  `locked` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '是否被锁\r\n',
  `lastloginaddr` char(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后登录IP',
  `logintimes` int(0) NULL DEFAULT NULL COMMENT '登录次数',
  `sid` int(0) NULL DEFAULT NULL COMMENT '员工id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sid`(`sid`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `staff` (`staffid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user1', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 46, 1);
INSERT INTO `user` VALUES (2, 'user2', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '::0', 0, 2);
INSERT INTO `user` VALUES (3, 'user3', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 52, 3);
INSERT INTO `user` VALUES (15, 'user4', '$2a$10$6SCtX1k1TUp3lHkeRnqVjODWMHQ1hQu9E0Oj8mXbjTHOhdawYcPhe', 1, 0, '0:0:0:0:0:0:0:1', 1, NULL);
INSERT INTO `user` VALUES (16, 'user5', '$2a$10$3IzMDU2x/lOQa07IKtYUuO1YloGM7yjxD7awQ2zjVsYfsbmR10EXK', 1, 0, '0:0:0:0:0:0:0:1', 20, 5);
INSERT INTO `user` VALUES (17, 'user6', '$2a$10$chkFjOSZflKL9Kch/EhD3.37P7hC87/NcPeta5JMOJexbYDgrpP.m', 1, 0, '0:0:0:0:0:0:0:1', 1, 6);
INSERT INTO `user` VALUES (18, 'notice', '$2a$10$o5npDWNTYwf4H.4OSA4v1.Lny0oHIF814hFRWTLjGrSdle3gF/HY6', 1, 0, '0:0:0:0:0:0:0:1', 1, 7);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `uid` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `rid` int(0) NULL DEFAULT NULL COMMENT '角色id',
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 3);
INSERT INTO `user_role` VALUES (15, 2);
INSERT INTO `user_role` VALUES (16, 2);
INSERT INTO `user_role` VALUES (17, 2);
INSERT INTO `user_role` VALUES (18, 5);

SET FOREIGN_KEY_CHECKS = 1;
