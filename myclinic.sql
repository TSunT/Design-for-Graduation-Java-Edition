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

 Date: 13/02/2022 18:01:31
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
  `parentid` int(11) DEFAULT NULL COMMENT '父级ID',
  `depcode` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '部门编码',
  `depattr` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '部门属性',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dep
-- ----------------------------
BEGIN;
INSERT INTO `dep` VALUES (1, '心内科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (2, '呼吸科', 0, NULL, NULL);
INSERT INTO `dep` VALUES (3, '血液科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (4, '消化科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (5, '内分泌科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (6, '免疫科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (7, '眼科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (8, '耳鼻喉科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (9, '口腔科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (10, '皮肤科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (11, '外科', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (12, '收费处', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (13, '药物管理处', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (14, '信息化处', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (15, '其它', NULL, NULL, NULL);
INSERT INTO `dep` VALUES (16, '总医院', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for dep_news
-- ----------------------------
DROP TABLE IF EXISTS `dep_news`;
CREATE TABLE `dep_news` (
  `id` int(11) NOT NULL COMMENT '新闻id',
  `newstitle` varchar(32) DEFAULT NULL COMMENT '新闻标题',
  `depid` int(11) DEFAULT NULL COMMENT '部门id',
  `newsdetail` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '新闻详情',
  `newsdate` date DEFAULT NULL COMMENT '发表时间',
  KEY `depid` (`depid`),
  CONSTRAINT `depid` FOREIGN KEY (`depid`) REFERENCES `dep` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `medicineid` int(11) NOT NULL AUTO_INCREMENT,
  `medicinename` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `medicinetype` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cost` int(10) unsigned NOT NULL,
  `rest` int(10) unsigned NOT NULL,
  PRIMARY KEY (`medicineid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of medicine
-- ----------------------------
BEGIN;
INSERT INTO `medicine` VALUES (1, '阿莫西林', '抗菌消炎类', 30, 4);
INSERT INTO `medicine` VALUES (2, '青霉素钠', '抗生素', 20, 99);
INSERT INTO `medicine` VALUES (3, '头孢拉定胶囊', '抗菌消炎类', 40, 94);
INSERT INTO `medicine` VALUES (4, '头孢他啶', '抗菌消炎类', 40, 192);
INSERT INTO `medicine` VALUES (5, '红霉素片', '抗菌消炎类', 20, 110);
INSERT INTO `medicine` VALUES (6, '利巴韦林', '抗病毒药', 40, 99);
INSERT INTO `medicine` VALUES (7, '阿昔洛韦', '抗病毒药', 40, 98);
INSERT INTO `medicine` VALUES (8, '阿苯达唑片', '抗寄生虫药', 45, 95);
INSERT INTO `medicine` VALUES (9, '左旋咪唑片', '抗寄生虫药', 46, 100);
INSERT INTO `medicine` VALUES (10, '卡马西平', '抗痢疾药', 30, 99);
INSERT INTO `medicine` VALUES (11, '新斯的明', '抗菌消炎类', 33, 8);
INSERT INTO `medicine` VALUES (12, '苯巴比妥注射液', '抗痢疾药', 60, 100);
INSERT INTO `medicine` VALUES (13, '琥珀胆碱', '抗胆碱药', 30, 100);
COMMIT;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id 主键',
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'vue-route的路由path',
  `component` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'vue-route的component',
  `title` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'vue-route的iconCls',
  `show` tinyint(1) DEFAULT '1',
  `parentId` int(11) DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `redirect` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `target` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `icon` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `hideChildrenInMenu` int(1) DEFAULT '0' COMMENT '是否显示子路由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, '', 'RouteView', 'menu.dashboard', 1, 0, 'dashboard', '/dashboard/workplace', NULL, 'dashboard', 0);
INSERT INTO `menu` VALUES (2, '', 'Workplace', 'menu.dashboard.workplace', 1, 1, 'workplace', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (3, 'https://pro.antdv.com/docs/getting-started', 'AdminUpdateUser', 'menu.dashboard.monitor', 1, 1, 'monitor', NULL, '_blank', NULL, 0);
INSERT INTO `menu` VALUES (4, '/dashboard/analysis', 'Analysis', 'menu.dashboard.analysis', 1, 1, 'Analysis', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (5, '', 'RouteView', '表单页', 1, 0, 'form', '/form/base-form', NULL, 'form', 0);
INSERT INTO `menu` VALUES (6, '', 'BasicForm', '基础表单', 1, 5, 'basic-form', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (7, '', 'StepForm', '分步表单', 1, 5, 'step-form', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (8, '', 'AdvanceForm', '高级表单', 1, 5, 'advanced-form', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (9, '', 'RouteView', '列表页', 1, 0, 'list', '/list/basic-list', NULL, 'table', 0);
INSERT INTO `menu` VALUES (10, '', 'StandardList', '标准列表', 1, 9, 'basic-list', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (12, '', 'CardList', '卡片列表', 1, 9, 'card', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (13, '', 'SearchLayout', '搜索列表', 1, 9, 'search', '/list/search/article', NULL, NULL, 0);
INSERT INTO `menu` VALUES (14, NULL, 'SearchArticles', '搜索列表（文章）', 1, 13, 'article', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (15, NULL, 'RouteView', '个人页', 1, 0, 'account', '/account/center', NULL, 'user', 0);
INSERT INTO `menu` VALUES (16, NULL, 'AccountCenter', '个人中心', 1, 15, 'center', NULL, NULL, '', 0);
INSERT INTO `menu` VALUES (17, NULL, 'UserListSummary', '用户汇总表', 1, 1, 'userlist', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (18, '/dashboard/userlist/userprofile/:id', 'UserProfile', '用户详情', 0, 1, 'userprofile', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (19, NULL, 'RoleListSummary', '权限汇总表', 1, 1, 'rolelist', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (20, '/dashboard/rolelist/rolesource/:id', 'RoleSource', '权限资源设置', 0, 1, 'rolesource', NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (21, NULL, 'DepListSummary', '部门汇总表', 1, 1, 'deplist', NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `menuid` int(11) NOT NULL COMMENT 'menuId',
  `roleid` int(11) NOT NULL COMMENT 'roleid',
  PRIMARY KEY (`menuid`,`roleid`) USING BTREE,
  KEY `roleid` (`roleid`) USING BTREE,
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`menuid`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`roleid`) REFERENCES `role` (`rid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
BEGIN;
INSERT INTO `menu_role` VALUES (1, 1);
INSERT INTO `menu_role` VALUES (2, 1);
INSERT INTO `menu_role` VALUES (3, 1);
INSERT INTO `menu_role` VALUES (4, 1);
INSERT INTO `menu_role` VALUES (5, 1);
INSERT INTO `menu_role` VALUES (6, 1);
INSERT INTO `menu_role` VALUES (7, 1);
INSERT INTO `menu_role` VALUES (8, 1);
INSERT INTO `menu_role` VALUES (9, 1);
INSERT INTO `menu_role` VALUES (10, 1);
INSERT INTO `menu_role` VALUES (12, 1);
INSERT INTO `menu_role` VALUES (13, 1);
INSERT INTO `menu_role` VALUES (14, 1);
INSERT INTO `menu_role` VALUES (17, 1);
INSERT INTO `menu_role` VALUES (18, 1);
INSERT INTO `menu_role` VALUES (19, 1);
INSERT INTO `menu_role` VALUES (20, 1);
INSERT INTO `menu_role` VALUES (21, 1);
INSERT INTO `menu_role` VALUES (1, 2);
INSERT INTO `menu_role` VALUES (2, 2);
INSERT INTO `menu_role` VALUES (3, 2);
INSERT INTO `menu_role` VALUES (5, 2);
INSERT INTO `menu_role` VALUES (7, 2);
INSERT INTO `menu_role` VALUES (15, 2);
INSERT INTO `menu_role` VALUES (16, 2);
INSERT INTO `menu_role` VALUES (1, 6);
INSERT INTO `menu_role` VALUES (2, 6);
INSERT INTO `menu_role` VALUES (15, 6);
INSERT INTO `menu_role` VALUES (16, 6);
COMMIT;

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `patientid` int(11) NOT NULL AUTO_INCREMENT COMMENT '病号',
  `patientidentity` char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '病人身份证号',
  `patientname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `patientgender` int(11) DEFAULT '0',
  `patienttel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `allergy` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `inputtime` date DEFAULT NULL,
  PRIMARY KEY (`patientid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='病人表';

-- ----------------------------
-- Records of patient
-- ----------------------------
BEGIN;
INSERT INTO `patient` VALUES (1, '320101198209076092', 'patient1', 0, '111111', 'xxx', NULL);
INSERT INTO `patient` VALUES (2, '320101198209078081', 'patient2', 0, '111111', 'xxxxx', NULL);
INSERT INTO `patient` VALUES (3, '320101198203082213', 'patient3', 0, '13321554231', 'xxxxxx', NULL);
INSERT INTO `patient` VALUES (4, '320101198405062139', 'patient4', 0, '13912313224', 'xxxxx', NULL);
INSERT INTO `patient` VALUES (5, '320104199702045413', 'patient5', 0, '13321554231', '无', '2020-10-26');
INSERT INTO `patient` VALUES (6, '320104198712140913', 'patient6', 0, '13321552222', '无', '2020-11-02');
INSERT INTO `patient` VALUES (7, '320104198511214413', 'patient7', 0, '13321554211', '无', '2020-11-02');
INSERT INTO `patient` VALUES (8, '320104198312140915', 'patient8', 0, '13321554231', '无', '2020-11-02');
INSERT INTO `patient` VALUES (9, '320104198212090916', 'patient9', 0, '13912313224', '无', '2020-11-02');
INSERT INTO `patient` VALUES (10, '320104197709020915', 'patient10', 0, '13912313224', '无', '2020-11-02');
INSERT INTO `patient` VALUES (11, '320104198302090967', 'patient11', 1, '', '无', '2020-11-02');
COMMIT;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `patientid` int(11) NOT NULL,
  `staffid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `totalcost` int(11) DEFAULT NULL,
  `paid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`patientid`,`staffid`,`time`) USING BTREE,
  KEY `staffid` (`staffid`) USING BTREE,
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`patientid`) REFERENCES `patient` (`patientid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`staffid`) REFERENCES `staff` (`staffid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of payment
-- ----------------------------
BEGIN;
INSERT INTO `payment` VALUES (1, 5, '2020-11-06 16:36:36', 120, 1);
INSERT INTO `payment` VALUES (3, 5, '2020-12-02 18:19:41', 120, 0);
INSERT INTO `payment` VALUES (4, 5, '2021-04-04 23:02:50', 140, 0);
INSERT INTO `payment` VALUES (5, 5, '2021-04-13 22:52:34', 165, 1);
INSERT INTO `payment` VALUES (7, 5, '2020-11-05 07:11:37', 60, 0);
INSERT INTO `payment` VALUES (8, 5, '2021-04-13 23:00:06', 136, 1);
INSERT INTO `payment` VALUES (9, 5, '2020-11-05 08:09:42', 50, 1);
INSERT INTO `payment` VALUES (9, 5, '2021-04-13 23:08:13', 130, 1);
INSERT INTO `payment` VALUES (10, 5, '2020-11-06 09:12:59', 160, 1);
INSERT INTO `payment` VALUES (10, 5, '2020-11-06 17:18:25', 150, 1);
COMMIT;

-- ----------------------------
-- Table structure for prescription
-- ----------------------------
DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription` (
  `staffid` int(11) NOT NULL,
  `patientid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `medicineid` int(11) NOT NULL,
  `num` int(10) unsigned NOT NULL,
  `take` tinyint(1) DEFAULT '0',
  `paid` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`staffid`,`patientid`,`time`,`medicineid`) USING BTREE,
  KEY `patientid` (`patientid`) USING BTREE,
  KEY `medicineid` (`medicineid`) USING BTREE,
  CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`staffid`) REFERENCES `staff` (`staffid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`patientid`) REFERENCES `patient` (`patientid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `prescription_ibfk_3` FOREIGN KEY (`medicineid`) REFERENCES `medicine` (`medicineid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of prescription
-- ----------------------------
BEGIN;
INSERT INTO `prescription` VALUES (5, 1, '2020-11-06 16:36:36', 1, 4, 1, 1);
INSERT INTO `prescription` VALUES (5, 3, '2020-12-02 18:19:41', 8, 2, 0, 0);
INSERT INTO `prescription` VALUES (5, 3, '2020-12-02 18:19:41', 10, 1, 0, 0);
INSERT INTO `prescription` VALUES (5, 4, '2021-04-04 23:02:50', 5, 1, 0, 0);
INSERT INTO `prescription` VALUES (5, 4, '2021-04-04 23:02:50', 6, 3, 0, 0);
INSERT INTO `prescription` VALUES (5, 5, '2021-04-13 22:52:34', 3, 3, 1, 1);
INSERT INTO `prescription` VALUES (5, 5, '2021-04-13 22:52:34', 8, 1, 1, 1);
INSERT INTO `prescription` VALUES (5, 7, '2020-11-05 07:11:37', 1, 2, 0, 0);
INSERT INTO `prescription` VALUES (5, 8, '2021-04-13 23:00:06', 1, 3, 0, 1);
INSERT INTO `prescription` VALUES (5, 8, '2021-04-13 23:00:06', 9, 1, 0, 1);
INSERT INTO `prescription` VALUES (5, 9, '2020-11-05 08:09:42', 2, 1, 1, 1);
INSERT INTO `prescription` VALUES (5, 9, '2020-11-05 08:09:42', 10, 1, 1, 1);
INSERT INTO `prescription` VALUES (5, 9, '2021-04-13 23:08:13', 6, 1, 1, 1);
INSERT INTO `prescription` VALUES (5, 9, '2021-04-13 23:08:13', 8, 2, 1, 1);
INSERT INTO `prescription` VALUES (5, 10, '2020-11-06 09:12:59', 4, 2, 1, 1);
INSERT INTO `prescription` VALUES (5, 10, '2020-11-06 09:12:59', 7, 2, 1, 1);
INSERT INTO `prescription` VALUES (5, 10, '2020-11-06 17:18:25', 1, 5, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rname` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'DOCTOR');
INSERT INTO `role` VALUES (3, 'PATIENT');
INSERT INTO `role` VALUES (4, 'PHARMACY');
INSERT INTO `role` VALUES (5, 'NOTICE');
INSERT INTO `role` VALUES (6, 'USER');
COMMIT;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staffid` int(11) NOT NULL,
  `staffname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `salary` int(10) unsigned NOT NULL,
  `staffgender` int(11) DEFAULT '0',
  `stafftel` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dep` int(11) DEFAULT NULL,
  `office` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  KEY `staffid` (`staffid`) USING BTREE,
  KEY `dep` (`dep`) USING BTREE,
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`dep`) REFERENCES `dep` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of staff
-- ----------------------------
BEGIN;
INSERT INTO `staff` VALUES (1, 'staff1', NULL, 10000, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (2, 'staff2', '其它', 10000, 0, '1111', 1, '1111');
INSERT INTO `staff` VALUES (3, 'staff3', NULL, 10000, 0, NULL, 3, NULL);
INSERT INTO `staff` VALUES (4, 'staff4', '医士', 10000, 0, NULL, 4, NULL);
INSERT INTO `staff` VALUES (5, 'staff5', '医士', 8000, 0, '13372009880', 3, 'office');
INSERT INTO `staff` VALUES (6, 'staff6', '医师', 10000, 0, '13372009881', 2, 'office');
INSERT INTO `staff` VALUES (7, 'notice', '其它', 5500, 1, '13513255414', 15, '1floor');
INSERT INTO `staff` VALUES (8, 'pharmacy', '其它', 8000, 0, '13816735577', 13, 'pharmacy');
INSERT INTO `staff` VALUES (9, 'doctor1', '医士', 9800, 0, '13513255499', 5, '内分泌科');
COMMIT;

-- ----------------------------
-- Table structure for treatment
-- ----------------------------
DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment` (
  `heartrate` int(10) unsigned DEFAULT NULL,
  `bloodpressure` int(10) unsigned DEFAULT NULL,
  `temperature` int(10) unsigned DEFAULT NULL,
  `patientsymptoms` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `presentillness` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `pastillness` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `diagnose` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `prescription` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `treatmentid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`treatmentid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of treatment
-- ----------------------------
BEGIN;
INSERT INTO `treatment` VALUES (64, 54, 37, '嗓子疼痛3天', '轻度咳嗽，咯白色粘痰，发热，无咯血。', '平时体质较差,易患感冒。无肝炎及结核病史。未作预防接种已近30 年，无眼痛、视力障碍，无耳流脓、耳痛、重听，无经常鼻阻塞、流脓 涕，无牙痛史。', '咽炎', '没开处方', 11);
INSERT INTO `treatment` VALUES (88, 23, 22, 'xx', 'xxx', 'xxxx', '', '没开处方', 13);
INSERT INTO `treatment` VALUES (88, 23, 22, '嗓子疼痛3天', '轻度咳嗽，咯白色粘痰，发热，无咯血。', '平时体质较差,易患感冒。无肝炎及结核病史。未作预防接种已近30 年，无眼痛、视力障碍，无耳流脓、耳痛、重听，无经常鼻阻塞、流脓 涕，无牙痛史。', '', '没开处方', 14);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, '', '', '', '', '没开处方', 15);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, '', '', '', '', '没开处方', 16);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, '', '', '', '', '没开处方', 17);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, 'hhh', 'kkk', 'ggg', '', '没开处方', 18);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, 'hhh', 'kkk', 'ggg', '冠心病 ', '没开处方', 19);
INSERT INTO `treatment` VALUES (64, 54, 37, 'hhh', 'kkk', 'ggg', '冠心病 ', '阿莫西林*2;', 20);
INSERT INTO `treatment` VALUES (64, 54, 36, '胸痛', '轻度咳嗽，咯白色粘痰，发热，无咯血。', 'xxxx', '', '没开处方', 21);
INSERT INTO `treatment` VALUES (64, 54, 36, '胸痛', '轻度咳嗽，咯白色粘痰，发热，无咯血。', 'xxxx', '', '没开处方', 22);
INSERT INTO `treatment` VALUES (64, 54, 37, '胸痛', '一月下旬再次出现胸闷、气急、心悸加重，夜间不能平卧，阵发性心前区隐痛，轻度咳嗽，咯白色粘痰，自觉无发热，无咯血。', 'xxxx', '冠心病 ', '青霉素钠*1;卡马西平*1;', 23);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, '', '', '', '', '没开处方', 24);
INSERT INTO `treatment` VALUES (64, 23, 37, '嗓子疼痛3天', '一月下旬再次出现胸闷、气急、心悸加重，夜间不能平卧，阵发性心前区隐痛，轻度咳嗽，咯白色粘痰，自觉无发热，无咯血。', '无耳流脓、耳痛、重听，无经常鼻阻塞、流脓 涕，无牙痛史。', '', '没开处方', 25);
INSERT INTO `treatment` VALUES (NULL, NULL, NULL, '', '', '', '', '没开处方', 26);
INSERT INTO `treatment` VALUES (66, 53, 36, 'hehe', 'haha', 'kkkk', 'kkkkkeeee', '头孢他啶*2;阿昔洛韦*2;', 27);
INSERT INTO `treatment` VALUES (88, 54, 37, '嗓子疼痛3天', '平时体质较差,易患感冒。无肝炎及结核病史。未作预防接种已近30 年。 系统回顾:无眼痛、视力障碍，无耳流脓、耳痛、重听，无经常鼻阻塞、流脓 涕，无牙痛史。', 'kkkk', '咽炎', '阿莫西林*4;', 28);
INSERT INTO `treatment` VALUES (88, 23, 37, '嗓子疼痛3天', '轻度咳嗽，咯白色粘痰，发热，无咯血。', 'kkkk', '咽炎', '阿莫西林*5;', 29);
INSERT INTO `treatment` VALUES (64, 23, 37, '嗓子疼痛3天', '一月下旬再次出现胸闷、气急、心悸加重，夜间不能平卧，阵发性心前区隐痛，轻度咳嗽，咯白色粘痰，自觉无发热，无咯血。', '无耳流脓、耳痛、重听，无经常鼻阻塞、流脓 涕，无牙痛史。', '', '阿苯达唑片*2;卡马西平*1;', 30);
INSERT INTO `treatment` VALUES (77, 99, 34, '232323', '23232322', '2323232', 'ddd', '利巴韦林*3;红霉素片*1;', 31);
INSERT INTO `treatment` VALUES (78, 54, 37, 'xxxx', 'xxxxx', 'xxxxx', 'xxxxx', '头孢拉定胶囊*3;阿苯达唑片*1;', 32);
INSERT INTO `treatment` VALUES (33, 44, 36, 'xxxx', 'xxxx', 'xxxx', 'xxxx', '阿莫西林*3;左旋咪唑片*1;', 33);
INSERT INTO `treatment` VALUES (99, 66, 36, 'xxx', 'xxx', 'xxx', 'xxx', '阿苯达唑片*2;利巴韦林*1;', 34);
COMMIT;

-- ----------------------------
-- Table structure for treatmentbrief
-- ----------------------------
DROP TABLE IF EXISTS `treatmentbrief`;
CREATE TABLE `treatmentbrief` (
  `patientid` int(11) NOT NULL,
  `staffid` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `treatmentid` int(11) DEFAULT NULL,
  `diagnose` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tbid` int(11) NOT NULL AUTO_INCREMENT,
  `completed` tinyint(1) DEFAULT '0',
  `patientname` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tbid`) USING BTREE,
  KEY `patientid` (`patientid`) USING BTREE,
  KEY `staffid` (`staffid`) USING BTREE,
  KEY `treatmentid` (`treatmentid`) USING BTREE,
  CONSTRAINT `treatmentbrief_ibfk_1` FOREIGN KEY (`patientid`) REFERENCES `patient` (`patientid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `treatmentbrief_ibfk_2` FOREIGN KEY (`staffid`) REFERENCES `staff` (`staffid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `treatmentbrief_ibfk_3` FOREIGN KEY (`treatmentid`) REFERENCES `treatment` (`treatmentid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of treatmentbrief
-- ----------------------------
BEGIN;
INSERT INTO `treatmentbrief` VALUES (10, 5, '2020-11-04 07:16:15', 11, '咽炎', 20, 1, 'patient10');
INSERT INTO `treatmentbrief` VALUES (3, 5, '2020-11-04 09:59:04', 17, '', 27, 1, 'patient3');
INSERT INTO `treatmentbrief` VALUES (7, 5, '2020-11-04 10:01:58', 20, '冠心病 ', 28, 1, 'patient7');
INSERT INTO `treatmentbrief` VALUES (6, 5, '2020-11-05 07:23:08', 22, '', 30, 0, 'patient6');
INSERT INTO `treatmentbrief` VALUES (9, 5, '2020-11-05 08:08:02', 23, '冠心病 ', 31, 1, 'patient9');
INSERT INTO `treatmentbrief` VALUES (3, 5, '2020-11-05 08:43:58', 30, '', 32, 1, 'patient3');
INSERT INTO `treatmentbrief` VALUES (1, 5, '2020-11-05 08:46:48', 28, '咽炎', 33, 1, 'patient1');
INSERT INTO `treatmentbrief` VALUES (10, 5, '2020-11-05 08:50:20', 29, '咽炎', 34, 1, 'patient10');
INSERT INTO `treatmentbrief` VALUES (10, 5, '2020-11-05 17:55:31', 27, 'kkkkkeeee', 35, 1, 'patient10');
INSERT INTO `treatmentbrief` VALUES (4, 5, '2021-04-04 23:01:48', 31, 'ddd', 36, 1, 'patient4');
INSERT INTO `treatmentbrief` VALUES (5, 5, '2021-04-13 22:51:18', 32, 'xxxxx', 37, 1, 'patient5');
INSERT INTO `treatmentbrief` VALUES (8, 5, '2021-04-13 22:58:56', 33, 'xxxx', 38, 1, 'patient8');
INSERT INTO `treatmentbrief` VALUES (9, 5, '2021-04-13 23:06:12', 34, 'xxx', 39, 1, 'patient9');
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
  `logintimes` int(11) DEFAULT '0' COMMENT '登录次数',
  `sid` int(11) DEFAULT NULL COMMENT '员工id',
  `avatar` varchar(144) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `staffname` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '员工称呼',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sid` (`sid`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `staff` (`staffid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'user1', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '127.0.0.1', 368, 1, 'ee519888bab540568bb35b870c7b77fc.jpg', '小林子');
INSERT INTO `user` VALUES (2, 'user2', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '127.0.0.1', 6, 2, 'd2a954d7f31e4e6a828de63178d0f4be.jpg', '小林子2');
INSERT INTO `user` VALUES (3, 'user3', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '127.0.0.1', 116, 3, '37d52089ee224c779eeec81d1da66e91.jpg', '小林3');
INSERT INTO `user` VALUES (15, 'user4', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 0, 1, '0:0:0:0:0:0:0:1', 0, 4, '97ffdfd3345e43eeb2a9cd6d8d2eb536.jpg', '小林4');
INSERT INTO `user` VALUES (16, 'user5', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 163, 5, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '小林5');
INSERT INTO `user` VALUES (17, 'user6', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 2, 6, 'e4241cadd7b04b3aa2894c39d53322da.jpg', '小林6');
INSERT INTO `user` VALUES (18, 'notice', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 1, '0:0:0:0:0:0:0:1', 43, 7, '00e29d4c490045cea22f669426dcc003.jpg', '小林6');
INSERT INTO `user` VALUES (19, 'pharmacy', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '0:0:0:0:0:0:0:1', 20, 8, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '小林8');
INSERT INTO `user` VALUES (20, 'doctor1', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', 1, 0, '127.0.0.1', 4, 9, 'e244b0011de541d3a9a0484cd112607d.jpg', '小林9');
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `rid` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`uid`,`rid`) USING BTREE,
  KEY `uid` (`uid`) USING BTREE,
  KEY `rid` (`rid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 6);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (2, 5);
INSERT INTO `user_role` VALUES (2, 6);
INSERT INTO `user_role` VALUES (3, 1);
INSERT INTO `user_role` VALUES (3, 6);
INSERT INTO `user_role` VALUES (15, 2);
INSERT INTO `user_role` VALUES (15, 6);
INSERT INTO `user_role` VALUES (16, 2);
INSERT INTO `user_role` VALUES (16, 6);
INSERT INTO `user_role` VALUES (17, 2);
INSERT INTO `user_role` VALUES (17, 6);
INSERT INTO `user_role` VALUES (18, 5);
INSERT INTO `user_role` VALUES (18, 6);
INSERT INTO `user_role` VALUES (19, 4);
INSERT INTO `user_role` VALUES (19, 6);
INSERT INTO `user_role` VALUES (20, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
