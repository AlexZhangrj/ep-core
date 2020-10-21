/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : ep_manager

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-01-28 21:55:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_job_user_org_position
-- ----------------------------
DROP TABLE IF EXISTS `t_job_user_org_position`;
CREATE TABLE `t_job_user_org_position` (
  `job_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_job_user_org_position
-- ----------------------------
INSERT INTO `t_job_user_org_position` VALUES ('1', '1', '0', '0', '000', '000', '2017-10-09 17:05:48', null, null, '2018-03-14 09:05:39', '1', '系统管理员');
INSERT INTO `t_job_user_org_position` VALUES ('32703862641000448', '12948627418775552', '0', '1', '000', '000', '2018-03-27 11:46:56', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('33101407743639552', '33101303561322496', '1', '2', '000', '000', '2018-03-29 16:26:21', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('33101483845091328', '33101303561322496', '0', '0', '000', '000', '2018-03-29 16:26:57', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('70574451581779968', '70574366592598016', '70572776842330112', '1', '001', '000', '2018-10-22 11:55:20', '1', '系统管理员', '2018-10-22 17:46:48', '1', '系统管理员');
INSERT INTO `t_job_user_org_position` VALUES ('70580709508513792', '12949137737646080', '70572776842330112', '1', '000', '000', '2018-10-22 12:45:04', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('70618676281212928', '33101303561322496', '70572776842330112', '1', '000', '000', '2018-10-22 17:46:48', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('70618676339933184', '70574366592598016', '70572776842330112', '1', '000', '000', '2018-10-22 17:46:48', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('70621920864763904', '33101303561322496', '70572776842330112', '70621710002421760', '000', '000', '2018-10-22 18:12:36', '70574366592598016', '磁云管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('70952049568645120', '70952038415990784', '70572776842330112', '70619593940402176', '000', '000', '2018-10-24 13:56:13', '70574366592598016', '磁云管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('70972845525565440', '1', '70572776842330112', '70621710002421760', '001', '000', '2018-10-24 16:41:29', '70574366592598016', '磁云管理员', '2018-10-24 16:44:36', '70574366592598016', '磁云管理员');
INSERT INTO `t_job_user_org_position` VALUES ('70973237072232448', '1', '70572776842330112', '70621710002421760', '000', '000', '2018-10-24 16:44:36', '70574366592598016', '磁云管理员', null, null, null);

-- ----------------------------
-- Table structure for t_job_user_project_dept_position
-- ----------------------------
DROP TABLE IF EXISTS `t_job_user_project_dept_position`;
CREATE TABLE `t_job_user_project_dept_position` (
  `job_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `project_dept_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `creater_id` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_job_user_project_dept_position
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_job_user_role`;
CREATE TABLE `t_job_user_role` (
  `id` bigint(20) NOT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_job_user_role
-- ----------------------------
INSERT INTO `t_job_user_role` VALUES ('1', '1', '1', '0', '000', null, '2017-10-18 16:02:42', null, '2018-03-14 09:05:39', '1', '系统管理员');
INSERT INTO `t_job_user_role` VALUES ('32703862657777664', '32703862641000448', '12948627418775552', '1', '000', null, '2018-03-27 11:46:56', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('33101407752028160', '33101407743639552', '33101303561322496', '2', '000', null, '2018-03-29 16:26:21', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('33101483853479936', '33101483845091328', '33101303561322496', '0', '000', null, '2018-03-29 16:26:57', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70574451592265728', '70574451581779968', '70574366592598016', '1', '001', null, '2018-10-22 11:55:20', null, '2018-10-22 17:46:48', '1', '系统管理员');
INSERT INTO `t_job_user_role` VALUES ('70580709518999552', '70580709508513792', '12949137737646080', '1', '000', null, '2018-10-22 12:45:04', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70618676293795840', '70618676281212928', '33101303561322496', '70618387750846464', '000', null, '2018-10-22 17:46:48', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70618676348321792', '70618676339933184', '70574366592598016', '70618387750846464', '000', null, '2018-10-22 17:46:48', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70621920871055360', '70621920864763904', '33101303561322496', '70619688561803264', '000', null, '2018-10-22 18:12:36', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70621920879443968', '70621920864763904', '33101303561322496', '70621804248432640', '000', null, '2018-10-22 18:12:36', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70952049581228032', '70952049568645120', '70952038415990784', '70619688561803264', '000', null, '2018-10-24 13:56:13', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70972845536051200', '70972845525565440', '1', '70621804248432640', '001', null, '2018-10-24 16:41:29', null, '2018-10-24 16:44:36', '70574366592598016', '磁云管理员');
INSERT INTO `t_job_user_role` VALUES ('70973237080621056', '70973237072232448', '1', '70618387750846464', '000', null, '2018-10-24 16:44:36', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('70973237086912512', '70973237072232448', '1', '70621804248432640', '000', null, '2018-10-24 16:44:36', null, null, null, null);

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org` (
  `org_id` bigint(20) NOT NULL,
  `org_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `org_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `org_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `com_id` bigint(20) DEFAULT NULL,
  `office_location` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_postcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_fax` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_contact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `id_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`org_id`),
  KEY `idx_org_id_path` (`id_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_org
-- ----------------------------
INSERT INTO `t_org` VALUES ('0', '根系统', 'ROOT', 'COM', '0', null, null, null, null, null, null, '/', '000', '000', '2017-10-01 20:49:29', null, null, null, null, null, '0');
INSERT INTO `t_org` VALUES ('1', '华图教育', 'HUATUEDU', 'COM', '1', null, null, null, null, null, '0', '/1/', '000', '000', '2017-10-09 17:03:06', null, null, null, null, null, '1');
INSERT INTO `t_org` VALUES ('2', '信息化管理部', 'DEPT.INFO', 'DEPT', '1', null, null, null, null, null, '1', '/1/2/', '000', '000', '2017-10-09 17:04:05', null, null, '2018-03-02 10:52:37', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('3', '信息化管理1部', 'DEPT.INFO1', 'DEPT', '1', null, null, null, null, null, '2', '/1/2/3/', '000', '000', '2017-10-24 14:27:47', null, null, '2018-03-02 10:52:37', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('4', '信息化管理2部', 'DEPT.INFO2', 'DEPT', '1', null, null, null, null, null, '2', '/1/2/4/', '000', '000', '2017-10-24 14:28:32', null, null, '2018-03-02 10:52:37', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('5', '人力行政部', 'DEPT.HR', 'DEPT', '1', null, null, null, null, null, '1', '/1/5/', '000', '000', '2017-10-24 14:29:21', null, null, '2017-12-13 22:55:42', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('6', '人力部', 'DEPT.HR1', 'DEPT', '1', null, null, null, null, null, '5', '/1/5/6/', '000', '000', '2017-10-24 14:29:44', null, null, null, null, null, '1');
INSERT INTO `t_org` VALUES ('7', '财务部', 'DEPT.FNCL', 'DEPT', '1', null, null, null, null, null, '1', '/1/7/', '000', '000', '2017-10-24 14:31:14', null, null, null, null, null, '1');
INSERT INTO `t_org` VALUES ('8', '北京分校', 'COM.BJ', 'COM', '8', null, null, null, null, null, '1', '/1/8', '000', '000', '2017-11-04 11:25:26', null, null, null, null, null, '1');
INSERT INTO `t_org` VALUES ('13889690845315072', '信息化3部', 'DEPT.INFO3', null, '1', null, null, null, null, null, '2', '/1/2/13889690845315072/', '000', '001', '2017-12-13 15:45:20', '1', '系统管理员', '2018-03-02 10:48:37', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('13901515599118336', '1', '1', null, null, null, null, null, null, null, '1', '/1/13901515599118336/', '000', '001', '2017-12-13 17:19:19', '1', '系统管理员', '2017-12-13 17:19:40', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('13904154827161600', '信息化管理3部', 'DEPT.INFO3', 'DEPT', '1', null, null, null, null, null, '2', '/1/2/13904154827161600/', '000', '001', '2017-12-13 17:40:17', '1', '系统管理员', '2018-03-02 10:48:37', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('15553893574377472', '南京分校', 'BS.NJ', 'COM', '15553893574377472', null, null, null, null, null, '1', '/1/15553893574377472/', '000', '000', '2017-12-22 20:11:14', '1', '系统管理员', '2017-12-22 20:14:22', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('15554345154117632', '山西分校', 'BS.SX', 'COM', '15554345154117632', null, null, null, null, null, '1', '/1/15554345154117632/', '000', '000', '2017-12-22 20:14:49', '1', '系统管理员', '2017-12-22 20:17:14', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('15554717656547328', '南宁分校', 'BS.NN', 'COM', '15554717656547328', null, null, null, null, null, '1', '/1/15554717656547328/', '000', '000', '2017-12-22 20:17:47', '1', '系统管理员', '2017-12-22 20:23:28', '1', '系统管理员', '1');
INSERT INTO `t_org` VALUES ('15555762960990208', '上海分校', 'BS.SH', 'COM', '15555762960990208', null, null, null, null, null, '1', '/1/15555762960990208/', '000', '000', '2017-12-22 20:26:06', '1', '系统管理员', null, null, null, '1');
INSERT INTO `t_org` VALUES ('23821282793488384', 'AAAAAAAAAAAAAAa', 'AAAAAAAAAAAAAAa', 'COM', '23821282793488384', null, null, null, null, null, '1', '/1/23821282793488384/', '001', '000', '2018-02-06 11:14:32', '1', '系统管理员', null, null, null, '1');
INSERT INTO `t_org` VALUES ('23821311304269824', 'bbbbbb', 'bbbbbb', 'DEPT', '1', null, null, null, null, null, '1', '/1/23821311304269824/', '000', '000', '2018-02-06 11:14:46', '1', '系统管理员', null, null, null, '1');
INSERT INTO `t_org` VALUES ('23821351498285056', 'ccccccc', 'ccccccc', 'DEPT', '1', null, null, null, null, null, '1', '/1/23821351498285056/', '000', '000', '2018-02-06 11:15:05', '1', '系统管理员', null, null, null, '1');
INSERT INTO `t_org` VALUES ('70570870931718144', 'aaa', 'aaa', 'COM', '70570870931718144', null, null, null, null, null, '0', '/70570870931718144/', '000', '001', '2018-10-22 11:26:53', '1', '系统管理员', '2018-10-22 11:45:43', '1', '系统管理员', '0');
INSERT INTO `t_org` VALUES ('70572776842330112', '磁云科技', 'iciyun', 'GROUP', '70572776842330112', null, null, null, null, null, '0', '/70572776842330112/', '000', '000', '2018-10-22 11:42:02', '1', '系统管理员', null, null, null, '70572776842330112');
INSERT INTO `t_org` VALUES ('70572908361023488', '智链金服', 'zljf', 'GROUP', '70572908361023488', null, null, null, null, null, '70572776842330112', '/70572776842330112/70572908361023488/', '000', '001', '2018-10-22 11:43:05', '1', '系统管理员', '2018-10-22 11:43:59', '1', '系统管理员', '70572776842330112');
INSERT INTO `t_org` VALUES ('70573049987989504', '智链金服', 'zljf', 'COM', '70573049987989504', null, null, null, null, null, '70572776842330112', '/70572776842330112/70573049987989504/', '000', '000', '2018-10-22 11:44:12', '1', '系统管理员', null, null, null, '70572776842330112');
INSERT INTO `t_org` VALUES ('70573209065357312', 'bbbb', 'bbb', 'COM', '70573209065357312', null, null, null, null, null, '0', '/70573209065357312/', '000', '001', '2018-10-22 11:45:28', '1', '系统管理员', '2018-10-22 11:45:43', '1', '系统管理员', '0');

-- ----------------------------
-- Table structure for t_position
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `position_id` bigint(20) NOT NULL,
  `position_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `position_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`position_id`),
  UNIQUE KEY `position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_position
-- ----------------------------
INSERT INTO `t_position` VALUES ('0', '根系统管理员', 'ROOT.SYS.MGR', '0', '000', '000', '2017-11-06 08:51:53', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('1', '公司管理员', 'COM.MGR', '0', '000', '000', '2017-12-07 10:03:40', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('2', '系统管理员', 'SYS.MGR', '1', '000', '000', '2017-12-07 10:03:41', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('3', '事业部管理员', 'DEPT.MGR', '1', '000', '000', '2017-12-07 10:08:42', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('28957434695385088', '财务主管', 'FN.MGR', '0', '000', '000', '2018-03-06 19:33:00', '1', '系统管理员', '2018-03-06 19:33:58', '1', '系统管理员');
INSERT INTO `t_position` VALUES ('70619593940402176', '开发人员', 'DEV', '70572776842330112', '000', '000', '2018-10-22 17:54:06', '70574366592598016', '磁云管理员', null, null, null);
INSERT INTO `t_position` VALUES ('70621710002421760', '维护人员', 'OPER', '70572776842330112', '000', '000', '2018-10-22 18:10:55', '70574366592598016', '磁云管理员', null, null, null);

-- ----------------------------
-- Table structure for t_privilege
-- ----------------------------
DROP TABLE IF EXISTS `t_privilege`;
CREATE TABLE `t_privilege` (
  `privilege_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `privilege_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `simple_url` varchar(2038) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon_class` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon_type` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ab` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `collapse` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort_value` decimal(19,4) DEFAULT NULL,
  `request_method` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_menu` tinyint(3) unsigned zerofill DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `id_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`privilege_id`),
  UNIQUE KEY `privilege_id` (`privilege_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35219153436540929 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_privilege
-- ----------------------------
INSERT INTO `t_privilege` VALUES ('1', '角色人员管理', 'manager:role.back.menu', '/role-management', 'fa fa-users', '', null, null, '1.0000', 'GET', '001', '0', '/1/', '000', '000', '2017-10-06 10:52:50', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('2', '组织机构人员管理', 'manager:org.back.menu', '/org-management', 'fa fa-globe', null, null, null, '2.0000', 'GET', '001', '0', '/2/', '000', '000', '2017-10-06 10:52:50', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('3', '事业部人员管理', 'manager:project-dept.back.menu', '/project-dept-management', 'fa fa-address-card', null, null, null, '3.0000', 'GET', '001', '0', '/3/', '000', '001', '2017-10-06 10:56:43', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('4', '账号管理', 'manager:account.back.menu', '/user-management', 'fa fa-user', null, null, null, '0.0000', 'GET', '001', '0', '/4/', '000', '000', '2017-10-06 10:56:43', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('5', '角色权限配置', 'manager:role-privilege.back.menu', '/role-privilege-setting', null, null, null, null, '0.0000', 'GET', '001', '0', '/5/', '000', '001', '2017-10-06 10:56:43', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('6', '权限项管理', 'manager:privilege.back.menu', '/privilege-management', 'fa fa-th', null, null, null, '21.0000', 'GET', '001', '0', '/6/', '000', '000', '2017-10-06 10:56:43', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('7', '菜单用数据接口', 'manager:menus', null, null, null, null, null, '0.0000', 'GET', '000', '0', '/7/', '000', '000', '2017-10-21 13:04:33', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('8', '查询jobs接口', 'manager:menus.query-jobs', null, null, null, null, null, '0.0000', 'GET', '000', '7', '/7/8/', '000', '000', '2017-10-21 13:13:54', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('9', '查询菜单代码接口', 'manager:menus.query-menu-codes', null, null, null, null, null, '0.0000', 'GET', '000', '7', '/7/9/', '000', '000', '2017-10-22 09:33:35', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('10', '切换职务接口', 'manager:menus.switch-job', null, null, null, null, null, '0.0000', 'GET', '000', '7', '/7/10/', '000', '000', '2017-10-22 09:35:23', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('11', '返回角色森林', 'manager:role.back.trees', null, null, null, null, null, '0.0000', 'GET', '000', '1', '/1/11/', '000', '000', '2017-10-22 20:31:52', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('13', '返回自己所在事业部', 'manager:project-dept.back.my-prjdpts', null, null, null, null, null, '0.0000', 'GET', '000', '18', '/3/18/13/', '000', '001', '2017-10-23 21:02:52', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('14', '返回组织机构树', 'manager:org.back.tree', null, null, null, null, null, '0.0000', 'GET', '000', '2', '/2/14/', '000', '000', '2017-10-24 13:41:39', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('15', '返回本公司所有事业部', 'manager:project-dept.back.com-all-prjdpts', null, null, null, null, null, '0.0000', 'GET', '000', '18', '/3/18/15/', '000', '001', '2017-10-27 10:04:23', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('16', '返回本公司及下属公司所有事业部', 'manager:project-dept.back.com-subcoms-all-prjdpts', null, null, null, null, null, '0.0000', 'GET', '000', '18', '/3/18/16/', '000', '001', '2017-10-27 10:06:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('17', '返回角色森林', 'manager:role.back.trees', null, null, null, null, null, '0.0000', 'GET', '000', '5', '/5/17/', '000', '000', '2017-10-28 22:19:24', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('18', '返回事业部森林', 'manager:project-dept.back.trees', null, null, null, null, null, '0.0000', 'GET', '000', '3', '/3/18/', '000', '001', '2017-10-28 22:39:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('19', '根事业部管理', 'manager:project-dept.back.trees-all', null, null, null, null, null, '0.0000', 'GET', '000', '18', '/3/18/19/', '000', '001', '2017-11-04 16:03:06', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('20', '职位人员管理', 'manager:position.back.menu', '/position-management', 'fa fa-universal-access', null, null, null, '20.0000', 'GET', '000', '0', '/20/', '000', '000', '2017-11-05 17:08:06', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('21', '返回职位森林', 'manager:position.back.trees', null, null, null, null, null, '0.0000', 'GET', '000', '20', '/20/21/', '000', '001', '2017-11-05 17:46:15', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('22', '根职位管理', 'manager:position.back.trees-all', null, null, null, null, null, '0.0000', 'GET', '000', '21', '/20/21/22/', '000', '001', '2017-11-05 20:59:32', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('23', '返回本集团所有职位', 'manager:position.back.trees-group-positions', null, null, null, null, null, '0.0000', 'GET', '000', '21', '/20/21/23/', '000', '001', '2017-11-05 21:02:35', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('24', '添加角色', 'manager:role.back.add', null, null, null, null, null, '0.0000', 'POST', '000', '1', '/1/24/', '000', '000', '2017-11-23 12:25:52', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('25', '多选删除角色', 'manager:role.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '1', '/1/25/', '000', '000', '2017-11-23 16:44:51', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('26', '修改角色', 'manager:role.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '1', '/1/26/', '000', '000', '2017-11-25 10:49:07', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('27', '移动角色', 'manager:role.back.move-to', null, null, null, null, null, '0.0000', 'PUT', '000', '1', '/1/27/', '000', '000', '2017-11-28 10:37:59', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('28', '返回账号管理列表', 'manager:user.back.page-query', null, null, null, null, null, '0.0000', 'GET', '000', '4', '/4/28/', '000', '000', '2017-12-02 15:42:07', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('29', '返回所有账号（包括所有集团）', 'manager:user.back.page-query-all-groups', null, null, null, null, null, '0.0000', 'GET', '000', '4', '/4/29/', '000', '000', '2017-12-02 16:17:20', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('30', '添加帐号', 'manager:user.back.add', null, null, null, null, null, '0.0000', 'POST', '000', '4', '/4/30/', '000', '000', '2017-12-08 08:27:54', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('31', '修改帐号', 'manager:user.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '4', '/4/31/', '000', '000', '2017-12-08 08:28:53', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('32', '删除帐号', 'manager:user.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '4', '/4/32/', '000', '000', '2017-12-08 08:30:05', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('33', '锁定账号', 'manager:user.back.lock', null, null, null, null, null, '0.0000', 'PUT', '000', '4', '/4/33/', '000', '000', '2017-12-10 14:16:37', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('34', '解锁账号', 'manager:user.back.unlock', null, null, null, null, null, '0.0000', 'PUT', '000', '4', '/4/34/', '000', '000', '2017-12-10 14:16:37', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('35', '锁定角色', 'manager:role.back.lock', null, null, null, null, null, '0.0000', 'PUT', '000', '1', '/1/35/', '000', '000', '2017-12-10 15:02:08', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('36', '解锁角色', 'manager:role.back.unlock', null, null, null, null, null, '0.0000', 'PUT', '000', '1', '/1/36/', '000', '000', '2017-12-10 15:02:08', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('37', '添加组织机构', 'manager:org.back.add', null, null, null, null, null, '0.0000', 'POST', '000', '2', '/2/37/', '000', '000', '2017-12-13 15:23:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('38', '多选删除组织机构', 'manager:org.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '2', '/2/38/', '000', '000', '2017-12-13 15:23:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('39', '修改组织机构', 'manager:org.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '2', '/2/39/', '000', '000', '2017-12-13 15:23:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('40', '移动组织机构', 'manager:org.back.move-to', null, null, null, null, null, '0.0000', 'PUT', '000', '2', '/2/40/', '000', '000', '2017-12-13 15:23:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('41', '锁定组织机构', 'manager:org.back.lock', null, null, null, null, null, '0.0000', 'PUT', '000', '2', '/2/41/', '000', '000', '2017-12-13 15:23:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('42', '解锁组织机构', 'manager:org.back.unlock', null, null, null, null, null, '0.0000', 'PUT', '000', '2', '/2/42/', '000', '000', '2017-12-13 15:23:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('43', '返回本集团所有账号', 'manager:user.back.page-query-all-coms', null, null, null, null, null, '0.0000', 'GET', '000', '4', '/4/43/', '000', '000', '2017-12-22 21:36:50', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('44', '返回所有事业部（包括所有集团）', 'manager:project-dept.back.page-query-all-groups', null, null, null, null, null, '0.0000', 'GET', '000', '46', '/3/45/46/44/', '000', '001', '2017-12-30 14:57:37', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('45', '返回本公司事业部管理列表', 'manager:project-dept.back.page-query', null, null, null, null, null, '0.0000', 'GET', '000', '3', '/3/45/', '000', '001', '2017-12-30 14:58:48', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('46', '返回本集团事业部管理列表', 'manager:project-dept.back.page-query-all-coms', null, null, null, null, null, '0.0000', 'GET', '000', '45', '/3/45/46/', '000', '001', '2017-12-30 16:06:01', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('47', '添加事业部', 'manager:project-dept.back.add', null, null, null, null, null, '0.0000', 'POST', '000', '3', '/3/47/', '000', '001', '2017-12-13 15:23:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('48', '多选删除事业部', 'manager:project-dept.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '3', '/3/48/', '000', '001', '2017-12-13 15:23:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('49', '修改事业部', 'manager:project-dept.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '3', '/3/49/', '000', '001', '2017-12-13 15:23:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('50', '移动事业部', 'manager:project-dept.back.move-to', null, null, null, null, null, '0.0000', 'PUT', '000', '3', '/3/50/', '000', '001', '2017-12-13 15:23:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('51', '锁定事业部', 'manager:project-dept.back.lock', null, null, null, null, null, '0.0000', 'PUT', '000', '3', '/3/51/', '000', '001', '2017-12-13 15:23:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('52', '解锁事业部', 'manager:project-dept.back.unlock', null, null, null, null, null, '0.0000', 'PUT', '000', '3', '/3/52/', '000', '001', '2017-12-13 15:23:16', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('53', '删除本公司事业部', 'manager:project-dept.back.delete-self-com', null, null, null, null, null, '0.0000', 'DELETE', '000', '48', '/3/48/53/', '000', '001', '2017-12-31 15:55:17', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('54', '删除本集团事业部', 'manager:project-dept.back.delete-self-group', null, null, null, null, null, '0.0000', 'DELETE', '000', '48', '/3/48/54/', '000', '001', '2017-12-31 15:55:17', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('55', '删除任何集团事业部', 'manager:project-dept.back.delete-all-groups', null, null, null, null, null, '0.0000', 'DELETE', '000', '48', '/3/48/55/', '000', '001', '2017-12-31 15:55:17', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('56', '添加本公司事业部', 'manager:project-dept.back.add-self-com', null, null, null, null, null, '0.0000', 'POST', '000', '47', '/3/47/56/', '000', '001', '2017-12-31 16:08:41', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('57', '添加本集团事业部', 'manager:project-dept.back.add-self-group', null, null, null, null, null, '0.0000', 'POST', '000', '47', '/3/47/57/', '000', '001', '2017-12-31 16:08:41', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('58', '添加任何集团事业部', 'manager:project-dept.back.add-all-groups', null, null, null, null, null, '0.0000', 'POST', '000', '47', '/3/47/58/', '000', '001', '2017-12-31 16:08:41', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('59', '修改本公司事业部', 'manager:project-dept.back.modify-self-com', null, null, null, null, null, '0.0000', 'PUT', '000', '49', '/3/49/59/', '000', '001', '2017-12-31 16:37:15', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('60', '修改本集团事业部', 'manager:project-dept.back.modify-self-group', null, null, null, null, null, '0.0000', 'PUT', '000', '49', '/3/49/60/', '000', '001', '2017-12-31 16:37:15', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('61', '修改任何集团事业部', 'manager:project-dept.back.modify-all-groups', null, null, null, null, null, '0.0000', 'PUT', '000', '49', '/3/49/61/', '000', '001', '2017-12-31 16:37:15', null, null, '2018-03-20 16:36:17', '1', '系统管理员');
INSERT INTO `t_privilege` VALUES ('62', '控制面板', 'manager:dashboard', '/dashboard', 'material-icons', 'dashboard', null, null, '-1.0000', 'GET', '001', '0', '/62/', '000', '000', '2018-02-03 09:26:38', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('63', '添加权限项', 'manager:privilege.back.add', '', '', '', '', '', '0.0000', 'POST', '000', '6', '/6/63/', '000', '000', '2017-12-13 15:23:16', null, '', '2018-03-02 11:17:34', null, '');
INSERT INTO `t_privilege` VALUES ('64', '多选删除权限项', 'manager:privilege.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '6', '/6/64/', '000', '000', '2018-03-02 11:18:39', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('65', '修改权限项', 'manager:privilege.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '6', '/6/65/', '000', '000', '2018-03-02 11:19:47', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('66', '移动权限项', 'manager:privilege.back.move-to', null, null, null, null, null, '0.0000', 'PUT', '000', '6', '/6/66/', '000', '000', '2018-03-02 11:21:08', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('67', '锁定权限项', 'manager:privilege.back.lock', null, null, null, null, null, '0.0000', 'PUT', '000', '6', '/6/67/', '000', '000', '2018-03-02 11:22:11', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('68', '解锁权限项', 'manager:privilege.back.unlock', null, null, null, null, null, '0.0000', 'PUT', '000', '6', '/6/68/', '000', '000', '2018-03-02 11:23:04', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('69', '返回权限项森林', 'manager:privilege.back.trees', null, null, null, null, null, '0.0000', 'GET', '000', '6', '/6/69/', '000', '000', '2018-03-02 11:27:21', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('70', '管理job', 'manager:job.back.manage-role-jobs.query', '', '', '', '', '', '0.0000', '', '000', '5', '/5/70/', '000', '000', '2018-03-03 15:48:09', '1', '系统管理员', null, null, null);
INSERT INTO `t_privilege` VALUES ('71', '职位管理', 'manager:position.back.menu', '/position-management', 'fa \r\nfa-id-card\r\nfa fa-id-card', null, null, null, '0.5000', 'GET', '001', '0', '/71/', '000', '000', '2018-03-06 16:49:50', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('72', '返回职位管理列表', 'manager:position.back.page-query', null, null, null, null, null, '0.0000', 'GET', '000', '71', '/71/72/', '000', '000', '2018-03-06 17:23:35', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('73', '返回所有职位（包括所有集团）', 'manager:position.back.page-query-all-groups', null, null, null, null, null, '0.0000', 'GET', '000', '71', '/71/73/', '000', '000', '2018-03-06 17:24:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('74', '添加职位', 'manager:position.back.add', '', null, null, null, null, '0.0000', 'POST', '000', '71', '/71/74/', '000', '000', '2018-03-06 17:25:38', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('75', '修改职位', 'manager:position.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '71', '/71/75/', '000', '000', '2018-03-06 17:28:35', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('76', '删除职位', 'manager:position.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '71', '/71/76/', '000', '000', '2018-03-06 17:29:21', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('77', '锁定职位', 'manager:position.back.lock', null, null, null, null, null, '0.0000', 'PUT', '000', '71', '/71/77/', '000', '000', '2018-03-06 17:30:13', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('78', '解锁职位', 'manager:position.back.unlock', null, null, null, null, null, '0.0000', 'PUT', '000', '71', '/71/78/', '000', '000', '2018-03-06 17:31:01', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('79', '配置向导', 'manager:guide', '/wizard', 'fa fa-address-card', null, null, null, '22.0000', 'GET', '001', '0', '/79/', '000', '000', '2018-03-07 15:27:42', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('80', '帐号机构职位角色一览', 'manager:job.back.integration-browse', '/integration-browse', 'fa fa-address-card', null, null, null, '23.0000', 'GET', '001', '0', '/80/', '000', '000', '2018-03-07 15:28:52', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('81', '保存配置', 'manager:config.back.save', null, null, null, null, null, '0.0000', 'PUT', '000', '79', '/79/81/', '000', '000', '2018-03-10 10:37:38', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('82', '返回本集团所有职位', 'manager:position.back.page-query-self-group', null, null, null, null, null, '0.0000', 'GET', '000', '71', '/71/82/', '000', '000', '2018-03-10 14:43:43', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('83', '加载账号机构职位角色配置', 'manager:config.back.load', null, null, null, null, null, '0.0000', 'GET', '000', '79', '/79/83/', '000', '000', '2018-03-10 15:57:32', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('84', '从指定角色中移除Job', 'manager:job.back.manage-role-jobs.delete', null, null, null, null, null, '0.0000', 'DELETE', '000', '70', '/5/70/84/', '000', '000', '2018-03-13 11:15:22', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('85', '管理职员roles', 'manager:job.back.manage-position-jobs-roles.query', null, null, null, null, null, '0.0000', 'GET', '000', '71', '/71/85/', '000', '000', '2018-03-14 12:36:53', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('86', '管理职位roles', 'manager:job.back.manage-user-jobs-roles.query', null, null, null, null, null, '0.0000', 'GET', '000', '4', '/4/86/', '000', '000', '2018-03-14 12:40:06', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('87', '管理职员roles', 'manager:job.back.manage-org-jobs-roles.query', null, null, null, null, null, '0.0000', 'GET', '000', '2', '/2/87/', '000', '000', '2018-03-14 13:06:47', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('88', '管理权限', 'manager:role.back.manage-privileges', null, null, null, null, null, '0.0000', 'GET', '000', '1', '/1/88/', '000', '000', '2018-03-20 15:33:14', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('89', '配置权限', 'manager:privilege.back.save-role-privileges', null, null, null, null, null, '0.0000', 'PUT', '000', '88', '/1/88/89/', '000', '000', '2018-03-20 15:35:04', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('90', '加载原始权限配置', 'manager:privilege.back.load-role-original-privileges', null, null, null, null, null, '0.0000', 'GET', '000', '88', '/1/88/90/', '000', '000', '2018-03-20 15:59:43', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('91', '加载当前用户的所有权限', 'manager:privilege.back.load-current-user-job-privileges', null, null, null, null, null, '0.0000', 'GET', '000', '88', '/1/88/91/', '000', '000', '2018-03-20 17:02:04', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('92', 'cms管理', 'cms:node.back.menu', '/cms-management', 'fa fa-list', null, null, null, '100.0000', 'GET', '001', '0', '/92/', '000', '000', '2019-01-28 12:14:02', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('93', '添加节点', 'cms:node.back.add', null, null, null, null, null, '0.0000', 'POST', '000', '92', '/92/93/', '000', '000', '2019-01-28 20:22:55', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('94', '修改节点', 'cms:node.back.modify', null, null, null, null, null, '0.0000', 'PUT', '000', '92', '/92/94/', '000', '000', '2019-01-28 20:24:18', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('95', '批量删除节点', 'cms:node.back.delete-by-ids', null, null, null, null, null, '0.0000', 'DELETE', '000', '92', '/92/95/', '000', '000', '2019-01-28 20:30:16', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('96', '移动节点', 'cms:node.back.move-to', null, null, null, null, null, '0.0000', 'PUT', '000', '92', '/92/96/', '000', '000', '2019-01-28 20:32:30', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('97', '返回节点树', 'cms:node.back.trees', null, null, null, null, null, '0.0000', 'GET', '000', '92', '/92/97/', '000', '000', '2017-10-24 13:41:39', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('98', '添加文章', 'cms:node.back.add-article', null, null, null, null, null, '0.0000', 'POST', '000', '92', '/92/98/', '000', '000', '2019-01-28 20:59:36', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('99', '修改文章', 'cms:node.back.modify-article', null, null, null, null, null, '0.0000', 'PUT', '000', '92', '/92/99/', '000', '000', '2019-01-28 21:00:31', null, null, null, null, null);
INSERT INTO `t_privilege` VALUES ('35218177046609920', '前端接口', 'fore:main', '', '', '', 'FM', 'frontMain', '-1.5000', 'GET', '000', '0', '/35218177046609920/', '000', '000', '2018-04-10 08:48:55', '33101303561322496', '公司管理员', null, null, null);
INSERT INTO `t_privilege` VALUES ('35218933621456896', '菜单用数据接口', 'manager:fore.menus', '', '', '', '', '', '0.0000', 'GET', '000', '35218177046609920', '/35218177046609920/35218933621456896/', '000', '000', '2018-04-10 08:54:56', '33101303561322496', '公司管理员', '2018-04-10 09:00:16', '33101303561322496', '公司管理员');
INSERT INTO `t_privilege` VALUES ('35219016979054592', '查询jobs接口', 'manager:fore.menus.query-jobs', '', '', '', '', '', '0.0000', 'GET', '000', '35218933621456896', '/35218177046609920/35218933621456896/35219016979054592/', '000', '000', '2018-04-10 08:55:35', '33101303561322496', '公司管理员', '2018-04-10 09:00:34', '33101303561322496', '公司管理员');
INSERT INTO `t_privilege` VALUES ('35219088680681472', '查询菜单代码接口', 'manager:fore.menus.query-menu-codes', '', '', '', '', '', '0.0000', 'GET', '000', '35218933621456896', '/35218177046609920/35218933621456896/35219088680681472/', '000', '000', '2018-04-10 08:56:10', '33101303561322496', '公司管理员', '2018-04-10 09:00:50', '33101303561322496', '公司管理员');
INSERT INTO `t_privilege` VALUES ('35219153436540928', '切换职务接口', 'manager:fore.menus.switch-job', '', '', '', '', '', '0.0000', 'GET', '000', '35218933621456896', '/35218177046609920/35218933621456896/35219153436540928/', '000', '000', '2018-04-10 08:56:41', '33101303561322496', '公司管理员', '2018-04-10 09:01:02', '33101303561322496', '公司管理员');

-- ----------------------------
-- Table structure for t_project_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_project_dept`;
CREATE TABLE `t_project_dept` (
  `project_dept_id` bigint(20) NOT NULL,
  `project_dept_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `project_dept_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `com_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `office_location` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_postcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_fax` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_contact` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `main_org_id` bigint(20) DEFAULT NULL,
  `main_org_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`project_dept_id`),
  UNIQUE KEY `project_dept_id` (`project_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_project_dept
-- ----------------------------
INSERT INTO `t_project_dept` VALUES ('1', '公职事业部', 'PP.CAREER', '1', '1', null, null, null, null, null, null, null, '2017-10-09 17:06:32', null, null, null, null, null, '000');
INSERT INTO `t_project_dept` VALUES ('2', '医疗事业部', 'M.CAREER', '1', '1', null, null, null, null, null, null, null, '2017-11-04 11:29:14', null, null, null, null, null, '000');
INSERT INTO `t_project_dept` VALUES ('3', '项目部1', 'PRJ.DPT', '8', '1', null, null, null, null, null, null, null, '2017-11-04 11:30:25', null, null, null, null, null, '000');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `id_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `role_parent_id` (`parent_id`) USING BTREE,
  KEY `role_name` (`role_name`) USING BTREE,
  KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70622506022600705 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('0', '根角色', 'SYS.ROOT', '000', '000', '2017-10-01 19:56:29', null, null, null, null, null, null, '/', '0');
INSERT INTO `t_role` VALUES ('1', '公司管理员', 'COM.MGR', '000', '000', '2017-10-09 17:01:26', null, null, null, null, null, '0', '/1/', '0');
INSERT INTO `t_role` VALUES ('2', '华图教育系统管理员', 'HUATU.SYS.MGR', '000', '000', '2017-10-18 16:04:57', null, null, null, null, null, '1', '/1/2/', '1');
INSERT INTO `t_role` VALUES ('3', '财务副总裁', 'CFO', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '2', '/1/2/3/', '1');
INSERT INTO `t_role` VALUES ('4', '财务总经理', 'F.MGR', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '3', '/1/2/3/4/', '1');
INSERT INTO `t_role` VALUES ('5', '财务部门经理', 'F.DEPT.MGR', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '4', '/1/2/3/4/5/', '1');
INSERT INTO `t_role` VALUES ('6', '财务部门副经理', 'F.DEPT.DMGR', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '5', '/1/2/3/4/5/6/', '1');
INSERT INTO `t_role` VALUES ('7', '财务普通员工', 'F.DEPT.EMP', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '6', '/1/2/3/4/5/6/7/', '1');
INSERT INTO `t_role` VALUES ('8', '业务副总裁', 'CBO', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '2', '/1/2/8/', '1');
INSERT INTO `t_role` VALUES ('9', '业务总经理', 'B.MGR', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '8', '/1/2/8/9/', '1');
INSERT INTO `t_role` VALUES ('10', '业务部门经理', 'B.DEPT.MGR', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '9', '/1/2/8/9/10/', '1');
INSERT INTO `t_role` VALUES ('11', '业务部门副经理', 'B.DEPT.DMGR', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '10', '/1/2/8/9/10/11/', '1');
INSERT INTO `t_role` VALUES ('12', '业务普通员工', 'B.DEPT.EMP', '000', '000', '2017-10-26 21:36:10', null, null, '2018-03-08 10:31:04', '1', '系统管理员', '11', '/1/2/8/9/10/11/12/', '1');
INSERT INTO `t_role` VALUES ('12965557410201600', '员工角色', 'EMP', '000', '000', '2017-12-08 13:20:59', null, null, null, null, null, '7', '/1/2/3/4/5/6/7/12965557410201600/', '1');
INSERT INTO `t_role` VALUES ('12967010325495808', 'aaaaa', 'bbbbbaaaa', '000', '000', '2017-12-08 13:32:32', '1', '系统管理员', '2017-12-08 13:33:23', '1', '系统管理员', '7', '/1/2/3/4/5/6/7/12967010325495808/', '1');
INSERT INTO `t_role` VALUES ('13889963376508928', 'TTTTTTEST', 'TTTTEST', '000', '000', '2017-12-13 15:47:30', '1', '系统管理员', '2018-03-08 10:31:04', '1', '系统管理员', '12', '/1/2/8/9/10/11/12/13889963376508928/', '1');
INSERT INTO `t_role` VALUES ('70573645136658432', '磁云科技管理员', 'ICIYUN.SYS.MGR', '000', '001', '2018-10-22 11:48:56', '1', '系统管理员', '2018-10-22 11:49:31', '1', '系统管理员', '1', '/1/70573645136658432/', '0');
INSERT INTO `t_role` VALUES ('70618387750846464', '磁云科技公司管理员', 'CIYUN.SYS.MGR', '000', '000', '2018-10-22 17:44:31', '1', '系统管理员', null, null, null, '1', '/1/70618387750846464/', '0');
INSERT INTO `t_role` VALUES ('70619688561803264', '开发人员', 'DEV', '000', '000', '2018-10-22 17:54:51', '70574366592598016', '磁云管理员', null, null, null, '70618387750846464', '/1/70618387750846464/70619688561803264/', '0');
INSERT INTO `t_role` VALUES ('70621804248432640', '维护人员', 'OPER', '000', '000', '2018-10-22 18:11:40', '70574366592598016', '磁云管理员', null, null, null, '70618387750846464', '/1/70618387750846464/70621804248432640/', '0');
INSERT INTO `t_role` VALUES ('70622506022600704', '使用人员', 'USER', '000', '000', '2018-10-22 18:17:15', '70574366592598016', '磁云管理员', null, null, null, '70618387750846464', '/1/70618387750846464/70622506022600704/', '70572776842330112');

-- ----------------------------
-- Table structure for t_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `t_role_privilege`;
CREATE TABLE `t_role_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `privilege_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70620211390185474 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_role_privilege
-- ----------------------------
INSERT INTO `t_role_privilege` VALUES ('1', '0', '1', '000', '2017-10-09 17:01:39', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('2', '0', '2', '000', '2017-10-09 17:01:46', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('3', '0', '3', '000', '2017-10-09 17:01:53', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('4', '0', '4', '000', '2017-10-09 17:01:55', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('5', '0', '5', '000', '2017-10-09 17:02:07', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('6', '0', '6', '000', '2017-10-09 17:02:15', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('7', '0', '7', '000', '2017-10-18 16:05:08', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('8', '0', '8', '000', '2017-10-21 13:22:44', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('9', '0', '9', '000', '2017-10-21 13:22:51', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('10', '0', '10', '000', '2017-10-22 09:37:16', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('11', '0', '11', '000', '2017-10-22 09:37:16', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('12', '0', '12', '000', '2017-10-22 20:42:44', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('13', '0', '13', '000', '2017-10-22 20:42:44', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('14', '0', '14', '000', '2017-10-23 21:03:54', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('15', '0', '15', '000', '2017-10-24 13:43:29', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('16', '0', '16', '000', '2017-11-04 14:52:58', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('17', '0', '17', '000', '2017-11-04 12:29:14', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('18', '0', '18', '000', '2017-11-04 12:29:14', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('19', '0', '19', '000', '2017-11-04 16:06:57', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('20', '0', '20', '000', '2017-11-04 16:06:57', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('21', '0', '21', '000', '2017-11-04 16:06:57', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('22', '0', '22', '000', '2017-11-04 16:08:35', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('23', '0', '23', '000', '2017-11-04 16:08:35', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('24', '0', '24', '000', '2017-11-04 20:13:12', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('25', '0', '25', '000', '2017-11-05 17:21:40', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('26', '0', '26', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('27', '0', '27', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('28', '0', '28', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('29', '0', '29', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('30', '0', '30', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('31', '0', '31', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('32', '0', '32', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33', '0', '33', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('34', '0', '34', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('35', '0', '35', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('36', '0', '36', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('37', '0', '37', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('38', '0', '38', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('39', '0', '39', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('40', '0', '40', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('41', '0', '41', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('42', '0', '42', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('43', '0', '43', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('44', '0', '44', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('45', '0', '45', '000', '2017-11-05 19:32:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('46', '0', '46', '000', '2017-11-05 22:35:52', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('47', '0', '47', '000', '2017-11-05 22:35:52', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('48', '0', '48', '000', '2017-11-05 22:35:52', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('49', '0', '49', '000', '2017-11-06 08:48:20', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('50', '0', '50', '000', '2017-11-06 08:49:01', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('51', '0', '51', '000', '2017-11-23 12:27:32', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('52', '0', '52', '000', '2017-11-23 16:45:33', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('53', '0', '53', '000', '2017-11-25 11:07:50', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('54', '0', '54', '000', '2017-11-28 10:38:43', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('55', '0', '55', '000', '2017-12-02 15:45:00', null, null, '2018-03-20 21:01:26', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('56', '0', '56', '000', '2017-12-02 18:08:01', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('57', '0', '57', '000', '2017-12-07 11:32:38', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('58', '0', '58', '000', '2017-12-07 11:32:42', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('59', '0', '59', '000', '2017-12-07 11:32:46', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('60', '0', '60', '000', '2017-12-07 11:32:49', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('61', '0', '61', '000', '2017-12-07 11:32:51', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('62', '0', '62', '000', '2017-12-07 11:32:53', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('63', '0', '63', '000', '2017-12-07 11:32:56', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('64', '0', '64', '000', '2017-12-07 11:32:58', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('65', '0', '65', '000', '2017-12-07 11:33:00', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('66', '0', '66', '000', '2017-12-07 11:33:21', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('67', '0', '67', '000', '2017-12-07 11:33:22', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('68', '0', '68', '000', '2017-12-07 11:33:24', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('69', '0', '69', '000', '2017-12-07 11:33:26', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70', '0', '70', '000', '2017-12-07 11:33:27', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('71', '0', '71', '000', '2017-12-07 11:33:29', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('72', '0', '72', '000', '2017-12-07 11:33:31', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('73', '0', '73', '000', '2017-12-07 11:33:34', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('74', '0', '74', '000', '2017-12-07 11:33:37', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('75', '0', '75', '000', '2017-12-07 11:33:39', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('76', '0', '76', '000', '2017-12-07 11:33:41', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('77', '0', '77', '000', '2017-12-07 11:33:43', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('78', '0', '78', '000', '2017-12-07 11:33:45', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('79', '0', '79', '000', '2017-12-07 11:33:48', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('80', '0', '80', '000', '2017-12-07 11:33:50', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('81', '0', '81', '000', '2017-12-07 11:33:56', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('82', '0', '82', '000', '2017-12-07 11:34:46', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('83', '0', '83', '000', '2017-12-07 11:34:49', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('84', '0', '84', '000', '2017-12-08 08:33:37', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('85', '0', '85', '000', '2017-12-08 08:33:46', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('86', '0', '86', '000', '2017-12-08 08:33:56', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('87', '0', '87', '000', '2017-12-08 11:54:29', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('88', '0', '88', '000', '2017-12-08 11:54:37', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('89', '0', '89', '000', '2017-12-08 11:54:44', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('90', '0', '90', '000', '2017-12-10 14:17:11', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('91', '0', '91', '000', '2017-12-10 14:17:11', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('92', '0', '92', '000', '2019-01-28 12:22:24', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('93', '0', '93', '000', '2019-01-28 20:56:13', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('94', '0', '94', '000', '2019-01-28 20:56:23', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('95', '0', '95', '000', '2019-01-28 20:56:32', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('96', '0', '96', '000', '2019-01-28 20:56:40', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('97', '0', '97', '000', '2019-01-28 20:56:54', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('98', '0', '98', '000', '2019-01-28 21:04:09', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('99', '0', '99', '000', '2019-01-28 21:04:19', null, null, null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33101801465053184', '2', '7', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801473441792', '2', '8', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801479733248', '2', '9', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801486024704', '2', '10', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801492316160', '2', '62', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801498607616', '2', '4', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801504899072', '2', '28', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801511190528', '2', '30', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801517481984', '2', '31', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801523773440', '2', '32', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801530064896', '2', '33', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801536356352', '2', '34', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801540550656', '2', '43', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801544744960', '2', '86', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801546842112', '2', '71', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801551036416', '2', '72', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801555230720', '2', '74', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801559425024', '2', '75', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801561522176', '2', '76', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801563619328', '2', '77', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801565716480', '2', '78', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801569910784', '2', '82', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801572007936', '2', '85', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801574105088', '2', '1', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801576202240', '2', '24', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801578299392', '2', '25', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801582493696', '2', '26', '001', '2018-03-29 16:29:28', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801586688000', '2', '27', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801590882304', '2', '35', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801592979456', '2', '36', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801595076608', '2', '88', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801597173760', '2', '89', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801599270912', '2', '90', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801601368064', '2', '91', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801603465216', '2', '2', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801605562368', '2', '14', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801607659520', '2', '37', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801609756672', '2', '38', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801613950976', '2', '39', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801618145280', '2', '40', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801620242432', '2', '41', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801624436736', '2', '42', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801628631040', '2', '87', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801630728192', '2', '20', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801632825344', '2', '79', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801634922496', '2', '81', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801639116800', '2', '83', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33101801643311104', '2', '80', '001', '2018-03-29 16:29:29', '1', '系统管理员', '2018-03-29 16:59:52', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('33105625539936256', '2', '7', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625546227712', '2', '8', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625550422016', '2', '9', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625556713472', '2', '10', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625560907776', '2', '62', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625565102080', '2', '4', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625571393536', '2', '28', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625575587840', '2', '29', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625581879296', '2', '30', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625586073600', '2', '31', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625596559360', '2', '32', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625602850816', '2', '33', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625604947968', '2', '34', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625609142272', '2', '43', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625611239424', '2', '86', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625615433728', '2', '71', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625617530880', '2', '72', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625621725184', '2', '73', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625625919488', '2', '74', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625628016640', '2', '75', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625632210944', '2', '76', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625636405248', '2', '77', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625640599552', '2', '78', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625644793856', '2', '82', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625648988160', '2', '85', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625653182464', '2', '1', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625655279616', '2', '11', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625676251136', '2', '24', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625680445440', '2', '25', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625682542592', '2', '26', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625686736896', '2', '27', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625688834048', '2', '35', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625690931200', '2', '36', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625695125504', '2', '88', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625697222656', '2', '89', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625701416960', '2', '90', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625705611264', '2', '91', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625709805568', '2', '2', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625713999872', '2', '14', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625718194176', '2', '37', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625720291328', '2', '38', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625724485632', '2', '39', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625728679936', '2', '40', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625732874240', '2', '41', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625737068544', '2', '42', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625741262848', '2', '87', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625743360000', '2', '20', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625747554304', '2', '79', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625751748608', '2', '81', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625760137216', '2', '83', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('33105625764331520', '2', '80', '000', '2018-03-29 16:59:52', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70574827173314560', '1', '62', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827183800320', '1', '4', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827190091776', '1', '28', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827194286080', '1', '29', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827200577536', '1', '30', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827206868992', '1', '31', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827213160448', '1', '32', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827219451904', '1', '33', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827225743360', '1', '34', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827232034816', '1', '43', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827240423424', '1', '86', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827246714880', '1', '7', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827253006336', '1', '8', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827259297792', '1', '9', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827263492096', '1', '10', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827267686400', '1', '71', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827282366464', '1', '72', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827288657920', '1', '73', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827292852224', '1', '74', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827299143680', '1', '75', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827303337984', '1', '76', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827309629440', '1', '77', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827315920896', '1', '78', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827320115200', '1', '82', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827326406656', '1', '85', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827330600960', '1', '1', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827336892416', '1', '11', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827343183872', '1', '24', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827368349696', '1', '25', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827374641152', '1', '26', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827380932608', '1', '27', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827387224064', '1', '35', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827399806976', '1', '36', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827408195584', '1', '88', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827414487040', '1', '89', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827420778496', '1', '90', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827433361408', '1', '91', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827441750016', '1', '2', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827456430080', '1', '14', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827473207296', '1', '37', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827496275968', '1', '38', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827504664576', '1', '39', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827519344640', '1', '40', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827538219008', '1', '41', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827544510464', '1', '42', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827550801920', '1', '87', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827565481984', '1', '20', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827578064896', '1', '6', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827594842112', '1', '63', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827603230720', '1', '64', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827611619328', '1', '65', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827632590848', '1', '66', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827643076608', '1', '67', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827649368064', '1', '68', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827653562368', '1', '69', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827666145280', '1', '79', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827682922496', '1', '81', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827687116800', '1', '83', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70574827701796864', '1', '80', '001', '2018-10-22 11:58:20', '1', '系统管理员', '2018-10-22 12:02:41', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374760673280', '1', '62', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374766964736', '1', '4', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374771159040', '1', '28', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374775353344', '1', '30', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374781644800', '1', '31', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374787936256', '1', '32', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374792130560', '1', '33', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374796324864', '1', '34', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374800519168', '1', '43', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374804713472', '1', '86', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374873919488', '1', '7', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374878113792', '1', '8', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374884405248', '1', '9', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374890696704', '1', '10', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374894891008', '1', '71', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374907473920', '1', '72', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374922153984', '1', '74', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374932639744', '1', '75', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374945222656', '1', '76', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374951514112', '1', '77', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374961999872', '1', '78', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374976679936', '1', '82', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374985068544', '1', '85', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374991360000', '1', '1', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575374997651456', '1', '11', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375001845760', '1', '24', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375016525824', '1', '25', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375022817280', '1', '26', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375031205888', '1', '27', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375035400192', '1', '35', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375041691648', '1', '36', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375047983104', '1', '88', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375052177408', '1', '89', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375058468864', '1', '90', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375062663168', '1', '91', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375066857472', '1', '2', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375071051776', '1', '14', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375077343232', '1', '37', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375094120448', '1', '38', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375102509056', '1', '39', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375108800512', '1', '40', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375115091968', '1', '41', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375119286272', '1', '42', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375133966336', '1', '87', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375142354944', '1', '20', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375148646400', '1', '79', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375159132160', '1', '81', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375165423616', '1', '83', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575375169617920', '1', '80', '001', '2018-10-22 12:02:41', '1', '系统管理员', '2018-10-22 12:05:46', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575763381813248', '1', '11', '001', '2018-10-22 12:05:46', '1', '系统管理员', '2018-10-22 12:07:29', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575763386007552', '1', '88', '001', '2018-10-22 12:05:46', '1', '系统管理员', '2018-10-22 12:07:29', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575763392299008', '1', '89', '001', '2018-10-22 12:05:46', '1', '系统管理员', '2018-10-22 12:07:29', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575763400687616', '1', '90', '001', '2018-10-22 12:05:46', '1', '系统管理员', '2018-10-22 12:07:29', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575763406979072', '1', '91', '001', '2018-10-22 12:05:46', '1', '系统管理员', '2018-10-22 12:07:29', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70575980065849344', '1', '62', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980072140800', '1', '4', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980076335104', '1', '28', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980080529408', '1', '29', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980101500928', '1', '30', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980114083840', '1', '31', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980128763904', '1', '32', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980135055360', '1', '33', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980149735424', '1', '34', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980170706944', '1', '43', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980181192704', '1', '86', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980193775616', '1', '7', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980200067072', '1', '8', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980204261376', '1', '9', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980210552832', '1', '10', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980223135744', '1', '71', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980239912960', '1', '72', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980258787328', '1', '73', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980267175936', '1', '74', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980275564544', '1', '75', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980279758848', '1', '76', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980288147456', '1', '77', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980300730368', '1', '78', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980307021824', '1', '82', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980319604736', '1', '85', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980330090496', '1', '1', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980342673408', '1', '11', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980348964864', '1', '24', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980355256320', '1', '25', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980361547776', '1', '26', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980376227840', '1', '27', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980382519296', '1', '35', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980388810752', '1', '36', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980401393664', '1', '88', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980405587968', '1', '89', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980413976576', '1', '90', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980418170880', '1', '91', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980422365184', '1', '2', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980432850944', '1', '14', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980447531008', '1', '37', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980455919616', '1', '38', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980472696832', '1', '39', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980478988288', '1', '40', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980489474048', '1', '41', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980504154112', '1', '42', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980520931328', '1', '87', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980527222784', '1', '20', '000', '2018-10-22 12:07:29', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980539805696', '1', '6', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980548194304', '1', '63', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980564971520', '1', '64', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980571262976', '1', '65', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980583845888', '1', '66', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980590137344', '1', '67', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980604817408', '1', '68', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980611108864', '1', '69', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980615303168', '1', '79', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980621594624', '1', '81', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980625788928', '1', '83', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70575980632080384', '1', '80', '000', '2018-10-22 12:07:30', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70618783053512704', '70618387750846464', '62', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783072387072', '70618387750846464', '4', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783078678528', '70618387750846464', '28', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783084969984', '70618387750846464', '29', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783091261440', '70618387750846464', '30', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783095455744', '70618387750846464', '31', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783099650048', '70618387750846464', '32', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783108038656', '70618387750846464', '33', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783114330112', '70618387750846464', '34', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783122718720', '70618387750846464', '43', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783129010176', '70618387750846464', '86', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783133204480', '70618387750846464', '7', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783137398784', '70618387750846464', '8', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783143690240', '70618387750846464', '9', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783152078848', '70618387750846464', '10', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783160467456', '70618387750846464', '71', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783168856064', '70618387750846464', '72', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783175147520', '70618387750846464', '73', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783181438976', '70618387750846464', '74', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783187730432', '70618387750846464', '75', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783196119040', '70618387750846464', '76', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783202410496', '70618387750846464', '77', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783210799104', '70618387750846464', '78', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783217090560', '70618387750846464', '82', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783227576320', '70618387750846464', '85', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783235964928', '70618387750846464', '1', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783242256384', '70618387750846464', '11', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783248547840', '70618387750846464', '24', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783256936448', '70618387750846464', '25', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783263227904', '70618387750846464', '26', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783269519360', '70618387750846464', '27', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783277907968', '70618387750846464', '35', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783282102272', '70618387750846464', '36', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783288393728', '70618387750846464', '88', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783303073792', '70618387750846464', '89', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783309365248', '70618387750846464', '90', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783326142464', '70618387750846464', '91', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783340822528', '70618387750846464', '2', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783351308288', '70618387750846464', '14', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783368085504', '70618387750846464', '37', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783374376960', '70618387750846464', '38', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783382765568', '70618387750846464', '39', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783389057024', '70618387750846464', '40', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783395348480', '70618387750846464', '41', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783403737088', '70618387750846464', '42', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783407931392', '70618387750846464', '87', '001', '2018-10-22 17:47:39', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783414222848', '70618387750846464', '20', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783428902912', '70618387750846464', '6', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783437291520', '70618387750846464', '63', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783451971584', '70618387750846464', '64', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783466651648', '70618387750846464', '65', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783479234560', '70618387750846464', '66', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783489720320', '70618387750846464', '67', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783506497536', '70618387750846464', '68', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618783531663360', '70618387750846464', '69', '001', '2018-10-22 17:47:40', '1', '系统管理员', '2018-10-22 17:49:11', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618975943262208', '70618387750846464', '62', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618975951650816', '70618387750846464', '4', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618975960039424', '70618387750846464', '28', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618975966330880', '70618387750846464', '29', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618975983108096', '70618387750846464', '30', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618975997788160', '70618387750846464', '31', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976010371072', '70618387750846464', '32', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976037634048', '70618387750846464', '33', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976052314112', '70618387750846464', '34', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976090062848', '70618387750846464', '43', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976098451456', '70618387750846464', '86', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976108937216', '70618387750846464', '7', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976125714432', '70618387750846464', '8', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976132005888', '70618387750846464', '9', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976138297344', '70618387750846464', '10', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976152977408', '70618387750846464', '71', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976163463168', '70618387750846464', '72', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976180240384', '70618387750846464', '73', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976188628992', '70618387750846464', '74', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976194920448', '70618387750846464', '75', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976211697664', '70618387750846464', '76', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976232669184', '70618387750846464', '77', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976255737856', '70618387750846464', '78', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976276709376', '70618387750846464', '82', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976283000832', '70618387750846464', '85', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976289292288', '70618387750846464', '1', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976312360960', '70618387750846464', '11', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976324943872', '70618387750846464', '24', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976343818240', '70618387750846464', '25', '001', '2018-10-22 17:49:11', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976350109696', '70618387750846464', '26', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976364789760', '70618387750846464', '27', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976373178368', '70618387750846464', '35', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976385761280', '70618387750846464', '36', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976404635648', '70618387750846464', '88', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976419315712', '70618387750846464', '89', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976429801472', '70618387750846464', '90', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976442384384', '70618387750846464', '91', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976446578688', '70618387750846464', '2', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976457064448', '70618387750846464', '14', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976465453056', '70618387750846464', '37', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976478035968', '70618387750846464', '38', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976501104640', '70618387750846464', '39', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976507396096', '70618387750846464', '40', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976511590400', '70618387750846464', '41', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976517881856', '70618387750846464', '42', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976522076160', '70618387750846464', '87', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70618976528367616', '70618387750846464', '20', '001', '2018-10-22 17:49:12', '1', '系统管理员', '2018-10-22 17:59:00', '1', '系统管理员');
INSERT INTO `t_role_privilege` VALUES ('70620210974949376', '70618387750846464', '62', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620210981240832', '70618387750846464', '4', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620210985435136', '70618387750846464', '28', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620210989629440', '70618387750846464', '29', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620210995920896', '70618387750846464', '30', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211000115200', '70618387750846464', '31', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211006406656', '70618387750846464', '32', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211014795264', '70618387750846464', '33', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211018989568', '70618387750846464', '34', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211025281024', '70618387750846464', '43', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211031572480', '70618387750846464', '86', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211037863936', '70618387750846464', '7', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211046252544', '70618387750846464', '8', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211052544000', '70618387750846464', '9', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211058835456', '70618387750846464', '10', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211065126912', '70618387750846464', '71', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211069321216', '70618387750846464', '72', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211075612672', '70618387750846464', '73', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211081904128', '70618387750846464', '74', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211088195584', '70618387750846464', '75', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211094487040', '70618387750846464', '76', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211100778496', '70618387750846464', '77', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211104972800', '70618387750846464', '78', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211111264256', '70618387750846464', '82', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211115458560', '70618387750846464', '85', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211123847168', '70618387750846464', '1', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211130138624', '70618387750846464', '11', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211138527232', '70618387750846464', '24', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211176275968', '70618387750846464', '25', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211182567424', '70618387750846464', '26', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211188858880', '70618387750846464', '27', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211195150336', '70618387750846464', '35', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211199344640', '70618387750846464', '36', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211205636096', '70618387750846464', '88', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211209830400', '70618387750846464', '89', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211245481984', '70618387750846464', '90', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211251773440', '70618387750846464', '91', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211260162048', '70618387750846464', '2', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211264356352', '70618387750846464', '14', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211270647808', '70618387750846464', '37', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211274842112', '70618387750846464', '38', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211281133568', '70618387750846464', '39', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211287425024', '70618387750846464', '40', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211312590848', '70618387750846464', '41', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211344048128', '70618387750846464', '42', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211350339584', '70618387750846464', '87', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211358728192', '70618387750846464', '20', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211362922496', '70618387750846464', '79', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211369213952', '70618387750846464', '81', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211377602560', '70618387750846464', '83', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);
INSERT INTO `t_role_privilege` VALUES ('70620211390185472', '70618387750846464', '80', '000', '2018-10-22 17:59:00', '1', '系统管理员', null, null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cellphone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `portrait_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `weixin` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `last_login_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `full_text` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=74563710865113089 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '系统管理员', '系统管理员', 'admin@huatu.com', '13910326704', '/img/02-01.jpg', null, '1981-12-01', '男', '000', null, null, '000', '2017-10-21 10:18:17', null, null, '2018-11-02 10:45:06', null, null, '0', '1:admin:系统管理员:系统管理员:admin@huatu.com:13910326704:1981-12-01 00:00:00:男');
INSERT INTO `t_user` VALUES ('12948627418775552', 'aaa', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'aaa', 'aa', 'aa', 'aa', null, null, '2017-12-02', '（未知）', '000', null, null, '000', '2017-12-08 11:06:26', '1', null, '2018-03-10 12:07:33', '1', '系统管理员', '0', '12948627418775552:aaa:aaa:aa:aa:aa:2017-12-02 00:00:00:（未知）');
INSERT INTO `t_user` VALUES ('12949137737646080', 'aaaaa', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '真实姓名', '昵称', 'email@email.com', '100000000', null, null, null, '男', '000', null, null, '000', '2017-12-08 11:10:30', '1', '系统管理员', '2017-12-10 13:55:41', '1', '系统管理员', '0', '12949137737646080:aaaaa:真实姓名:昵称:email@email.com:100000000::男');
INSERT INTO `t_user` VALUES ('12952271639805952', 'cccc', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '真实姓名', '昵称', 'email', '10000000', null, null, null, '男', '000', null, null, '000', '2017-12-08 11:35:24', '1', '系统管理员', null, null, null, '0', null);
INSERT INTO `t_user` VALUES ('12953029835751424', 'ccccd', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '真实姓名', '昵称', 'email', '10000000', null, null, null, '男', '000', null, null, '000', '2017-12-08 11:41:25', '1', '系统管理员', '2017-12-10 13:55:27', '1', '系统管理员', '0', '12953029835751424:ccccd:真实姓名:昵称:email:10000000::男');
INSERT INTO `t_user` VALUES ('12954326488055808', 'ddddd', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'eeee', 'fffff', 'ggggg', '11111111', null, null, '2017-12-16', '（未知）', '000', null, null, '000', '2017-12-08 11:51:44', '1', '系统管理员', null, null, null, '0', '12954326488055808:ddddd:eeee:fffff:ggggg:11111111:2017-12-16 00:00:00:（未知）');
INSERT INTO `t_user` VALUES ('12954379130765312', 'ddddde', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'eeee', 'fffff', '', '11111111', null, null, null, '（未知）', '000', null, null, '000', '2017-12-08 11:52:09', '1', '系统管理员', null, null, null, '0', '12954379130765312:ddddde:eeee:fffff::11111111::（未知）');
INSERT INTO `t_user` VALUES ('12954786605301760', 'ddddddddddd', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'ffffffffff', 'ggggggggg', 'hhhhhhhh', 'iiiiiiiii', null, null, '2017-12-30', '男', '000', null, null, '000', '2017-12-08 11:55:23', '1', '系统管理员', null, null, null, '1', '12954786605301760:ddddddddddd:ffffffffff:ggggggggg:hhhhhhhh:iiiiiiiii:2017-12-30 00:00:00:男');
INSERT INTO `t_user` VALUES ('13345913443450880', 'aaabbbbbbbcccccc', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:43:47', '1', '系统管理员', '2017-12-16 19:34:41', '1', '系统管理员', '0', '13345913443450880:aaabbbbbbbcccccc:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13345931657216000', 'aaabbbbbbbccccccdd', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:43:56', '1', '系统管理员', '2017-12-10 15:44:28', '1', '系统管理员', '0', '13345931657216000:aaabbbbbbbccccccdd:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13345945253052416', 'aaabbbbbbbccccccddeee', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:44:02', '1', '系统管理员', '2017-12-10 15:44:39', '1', '系统管理员', '0', '13345945253052416:aaabbbbbbbccccccddeee:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13345962164486144', 'aaabbbbbbbccccccddeeefff', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:44:10', '1', '系统管理员', '2017-12-10 15:44:50', '1', '系统管理员', '0', '13345962164486144:aaabbbbbbbccccccddeeefff:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13346082838806528', 'user1', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '张三', '张三的工作号', '', '', '/img/02-01.jpg', null, null, '男', '000', null, null, '000', '2017-12-10 15:45:08', '1', '系统管理员', '2018-11-15 13:31:37', null, null, '0', '13346082838806528:user1:张三:张三的工作号::::男');
INSERT INTO `t_user` VALUES ('13346100494729216', 'user2', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '李四', '李四的工作号', '', '', '/img/02-01.jpg', null, null, '男', '000', null, null, '000', '2017-12-10 15:45:16', '1', '系统管理员', '2018-11-15 13:33:00', null, null, '0', '13346100494729216:user2:李四:李四的工作号::::男');
INSERT INTO `t_user` VALUES ('13346122449813504', 'user3', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '王五', '王五的工作号', '', '', '/img/02-01.jpg', null, null, '男', '000', null, null, '000', '2017-12-10 15:45:27', '1', '系统管理员', '2018-11-15 13:39:14', null, null, '0', '13346122449813504:user3:王五:王五的工作号::::男');
INSERT INTO `t_user` VALUES ('13855239046168576', 'user4', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '赵六', '赵六的工作号', '', '', '/img/02-01.jpg', null, '2017-12-07', '男', '000', null, null, '000', '2017-12-13 11:11:32', '1', '系统管理员', '2018-11-15 14:22:14', null, null, '0', '13855239046168576:user4:赵六:赵六的工作号:::2017-12-07 00:00:00:男');
INSERT INTO `t_user` VALUES ('15722535150682112', '1111', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '11111', '1111', '1111', '11111', null, null, '2017-12-31', '男', '000', null, null, '000', '2017-12-23 18:31:28', '1', '系统管理员', null, null, null, '0', '15722535150682112:1111:11111:1111:1111:11111:2017-12-31 00:00:00:男');
INSERT INTO `t_user` VALUES ('28960771325034496', 'ceshi', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '测试', '测试', 'ceshi@ceshi.ceshi', '111111', null, null, null, '（未知）', '000', null, null, '000', '2018-03-06 19:59:31', '1', '系统管理员', null, null, null, '1', '28960771325034496:ceshi:测试:测试:ceshi@ceshi.ceshi:111111::（未知）');
INSERT INTO `t_user` VALUES ('29615289918291968', 'htAdmin', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '华图管理员', '管理员', 'ht@ht.com', '111111111', null, null, null, '（未知）', '000', null, null, '000', '2018-03-10 10:41:10', '1', '系统管理员', null, null, null, '1', '29615289918291968:htAdmin:华图管理员:管理员:ht@ht.com:111111111::（未知）');
INSERT INTO `t_user` VALUES ('29656955161149440', 'htEmp', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '华图好员工', '华图好员工', 'htemp@ht.com', '111111', null, null, null, '男', '000', null, null, '000', '2018-03-10 16:12:18', '1', '系统管理员', null, null, null, '1', '29656955161149440:htEmp:华图好员工:华图好员工:htemp@ht.com:111111::男');
INSERT INTO `t_user` VALUES ('33101303561322496', 'comsAdmin', '26b3cf65a2167b49f26161a6433b24bc', 'idptGf),', '公司管理员', '公司管理员', 'coms_admin@admin.com', '1111111111', null, null, null, '（未知）', '000', null, null, '000', '2018-03-29 16:25:31', '1', '系统管理员', null, null, null, '0', '33101303561322496:comsAdmin:公司管理员:公司管理员:coms_admin@admin.com:1111111111::（未知）');
INSERT INTO `t_user` VALUES ('70574366592598016', 'cyMgr', 'e5d3e05bc5169ca69f2f52f838680ce8', '_6+\\d54d', '磁云管理员', '磁云管理员', 'admin@iciyun.com', '13911111111', null, null, '2018-10-22', '男', '000', null, null, '000', '2018-10-22 11:54:40', '1', '系统管理员', null, null, null, '0', '70574366592598016:cyMgr:磁云管理员:磁云管理员:admin@iciyun.com:13911111111:2018-10-22 00:00:00:男');
INSERT INTO `t_user` VALUES ('70574976561840128', 'aaabbbb', 'b15c4222d8c85db7f60ffe87097781ea', '&>h{JnTr', 'aaa', 'aaa', '111@111.111', '13911111111', null, null, '2018-10-22', '男', '000', null, null, '000', '2018-10-22 11:59:31', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70574976561840128:aaabbbb:aaa:aaa:111@111.111:13911111111:2018-10-22 00:00:00:男');
INSERT INTO `t_user` VALUES ('70946240927367168', '123', '262e8ded27b0f67efb49a65ad7e617bb', '7{5(y>R-', '123', '123', '123@123.123', '13912341234', null, null, '2018-10-24', '男', '000', null, null, '000', '2018-10-24 13:10:03', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70946240927367168:123:123:123:123@123.123:13912341234:2018-10-24 00:00:00:男');
INSERT INTO `t_user` VALUES ('70948247734059008', '1234', '7c8b107a615bb52dc7aa34a84e12638f', 'GVbA6i,\\', '1234', '1234', '1234@1234.1234', '13911111111', null, null, '2018-10-24', '男', '000', null, null, '000', '2018-10-24 13:26:00', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70948247734059008:1234:1234:1234:1234@1234.1234:13911111111:2018-10-24 00:00:00:男');
INSERT INTO `t_user` VALUES ('70948770518401024', '11111', 'dcca58ec811bc7c1b77e8cb1ec557133', 'n;,:r&{6', '1111', '1111', '1111@1111.1111', '13910326704', null, null, '2018-10-24', '男', '000', null, null, '000', '2018-10-24 13:30:10', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70948770518401024:11111:1111:1111:1111@1111.1111:13910326704:2018-10-24 00:00:00:男');
INSERT INTO `t_user` VALUES ('70949263068102656', '111', '6b73911be526865d5a15819f456b1fe8', 't*1O2I\\g', '111', '111', '111@111.111', '13911111111', null, null, '2018-10-24', '男', '000', null, null, '000', '2018-10-24 13:34:04', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70949263068102656:111:111:111:111@111.111:13911111111:2018-10-24 00:00:00:男');
INSERT INTO `t_user` VALUES ('70951515489042432', '1', '3a53a3a4e52cf587c53fa0561482283c', '<M|gn4]m', '1', '1', '1@1.1', '13911111111', null, null, null, '男', '000', null, null, '000', '2018-10-24 13:51:59', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70951515489042432:1:1:1:1@1.1:13911111111::男');
INSERT INTO `t_user` VALUES ('70952038415990784', '2', '4a5d04905da710853a2bedbbee3a3fd4', '+ZFT|hf#', '2', '2', '2@2.2', '13922222222', null, null, null, '男', '000', null, null, '000', '2018-10-24 13:56:08', '70574366592598016', '磁云管理员', null, null, null, '70572776842330112', '70952038415990784:2:2:2:2@2.2:13922222222::男');
INSERT INTO `t_user` VALUES ('72215902579326976', 'user5', '1ace9b25fab52da89b60a1d3e42f43ce', 'G$,#&b7g', 'user5', 'user5', 'user5@aaa.aaa', '13911111111', '/img/02-01.jpg', null, '2018-11-07', '男', '000', null, null, '000', '2018-10-31 13:20:25', null, null, null, null, null, '5000000', '72215902579326976:user5:user5:user5:user5@aaa.aaa:13911111111:2018-11-07 00:00:00:男');
INSERT INTO `t_user` VALUES ('74563710865113088', 'user10', '227e621861ed7e1a46699dce5fccae9d', 'kyR?L&u)', 'user10', '用户10', '', '', '/img/02-01.jpg', null, null, '男', '000', null, null, '000', '2018-11-13 12:19:08', null, null, null, null, null, '5000000', '74563710865113088:user10:user10:用户10::::男');

-- ----------------------------
-- Table structure for t_user_org_position
-- ----------------------------
DROP TABLE IF EXISTS `t_user_org_position`;
CREATE TABLE `t_user_org_position` (
  `job_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_user_org_position
-- ----------------------------
INSERT INTO `t_user_org_position` VALUES ('1', '1', '1', '1', '000', '2017-10-09 17:05:48', null, null, null, null, null);
INSERT INTO `t_user_org_position` VALUES ('3', '1', '0', '1', '000', '2017-10-25 20:51:01', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_user_project_dept_position
-- ----------------------------
DROP TABLE IF EXISTS `t_user_project_dept_position`;
CREATE TABLE `t_user_project_dept_position` (
  `job_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `project_dept_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_user_project_dept_position
-- ----------------------------
INSERT INTO `t_user_project_dept_position` VALUES ('2', '1', '1', '1', '000', null, '2017-10-09 17:06:52', null, null, null, null);
