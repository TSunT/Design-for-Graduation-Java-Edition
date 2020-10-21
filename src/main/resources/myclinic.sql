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

 Date: 21/10/2020 17:51:54
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
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `rid` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rname` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'DOCTOR');
INSERT INTO `role` VALUES (3, 'PATIENT');

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
INSERT INTO `staff` VALUES (3, 'staff3', NULL, 10000, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (4, 'staff4', NULL, 10000, 0, NULL, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user1', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 36, 1);
INSERT INTO `user` VALUES (2, 'user2', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '::0', 0, 2);
INSERT INTO `user` VALUES (3, 'user3', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 0, NULL, '::0', NULL, 3);

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

SET FOREIGN_KEY_CHECKS = 1;
