/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : ep_manager

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-04-12 08:48:27
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
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_job_user_org_position
-- ----------------------------
INSERT INTO `t_job_user_org_position` VALUES ('1', '1', '0', '0', '000', '000', '2017-10-09 17:05:48', null, null, '2018-03-14 09:05:39', '1', '系统管理员');
INSERT INTO `t_job_user_org_position` VALUES ('32703862641000448', '12948627418775552', '0', '1', '000', '000', '2018-03-27 11:46:56', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('33101407743639552', '33101303561322496', '1', '2', '000', '000', '2018-03-29 16:26:21', '1', '系统管理员', null, null, null);
INSERT INTO `t_job_user_org_position` VALUES ('33101483845091328', '33101303561322496', '0', '0', '000', '000', '2018-03-29 16:26:57', '1', '系统管理员', null, null, null);

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
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_job_user_role
-- ----------------------------
INSERT INTO `t_job_user_role` VALUES ('1', '1', '1', '0', '000', null, '2017-10-18 16:02:42', null, '2018-03-14 09:05:39', '1', '系统管理员');
INSERT INTO `t_job_user_role` VALUES ('32703862657777664', '32703862641000448', '12948627418775552', '1', '000', null, '2018-03-27 11:46:56', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('33101407752028160', '33101407743639552', '33101303561322496', '2', '000', null, '2018-03-29 16:26:21', null, null, null, null);
INSERT INTO `t_job_user_role` VALUES ('33101483853479936', '33101483845091328', '33101303561322496', '0', '000', null, '2018-03-29 16:26:57', null, null, null, null);

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org` (
  `org_id` bigint(20) NOT NULL,
  `org_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `org_code` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `org_type` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `com_id` bigint(20) DEFAULT NULL,
  `office_location` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `office_postcode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `office_tel` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `office_fax` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `office_contact` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `id_path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`org_id`),
  KEY `idx_org_id_path` (`id_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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

-- ----------------------------
-- Table structure for t_position
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `position_id` bigint(20) NOT NULL,
  `position_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `position_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`position_id`),
  UNIQUE KEY `position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_position
-- ----------------------------
INSERT INTO `t_position` VALUES ('0', '根系统管理员', 'ROOT.SYS.MGR', '0', '000', '000', '2017-11-06 08:51:53', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('1', '公司管理员', 'COM.MGR', '0', '000', '000', '2017-12-07 10:03:40', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('2', '系统管理员', 'SYS.MGR', '1', '000', '000', '2017-12-07 10:03:41', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('3', '事业部管理员', 'DEPT.MGR', '1', '000', '000', '2017-12-07 10:08:42', null, null, null, null, null);
INSERT INTO `t_position` VALUES ('28957434695385088', '财务主管', 'FN.MGR', '0', '000', '000', '2018-03-06 19:33:00', '1', '系统管理员', '2018-03-06 19:33:58', '1', '系统管理员');

-- ----------------------------
-- Table structure for t_privilege
-- ----------------------------
DROP TABLE IF EXISTS `t_privilege`;
CREATE TABLE `t_privilege` (
  `privilege_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `privilege_code` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `simple_url` varchar(2038) COLLATE utf8_bin DEFAULT NULL,
  `icon_class` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `icon_type` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `ab` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `collapse` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `sort_value` decimal(19,4) DEFAULT NULL,
  `request_method` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `is_menu` tinyint(3) unsigned zerofill DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `id_path` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`privilege_id`),
  UNIQUE KEY `privilege_id` (`privilege_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35219153436540929 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
INSERT INTO `t_privilege` VALUES ('35218177046609920', '前端接口', 'fore:main', '', '', '', 'FM', 'frontMain', '-1.5000', 'GET', '000', '0', '/35218177046609920/', '000', '000', '2018-04-10 08:48:55', '33101303561322496', '公司管理员', null, null, null);
INSERT INTO `t_privilege` VALUES ('35218933621456896', '菜单用数据接口', 'manager:fore.menus', '', '', '', '', '', null, 'GET', '000', '35218177046609920', '/35218177046609920/35218933621456896/', '000', '000', '2018-04-10 08:54:56', '33101303561322496', '公司管理员', '2018-04-10 09:00:16', '33101303561322496', '公司管理员');
INSERT INTO `t_privilege` VALUES ('35219016979054592', '查询jobs接口', 'manager:fore.menus.query-jobs', '', '', '', '', '', null, 'GET', '000', '35218933621456896', '/35218177046609920/35218933621456896/35219016979054592/', '000', '000', '2018-04-10 08:55:35', '33101303561322496', '公司管理员', '2018-04-10 09:00:34', '33101303561322496', '公司管理员');
INSERT INTO `t_privilege` VALUES ('35219088680681472', '查询菜单代码接口', 'manager:fore.menus.query-menu-codes', '', '', '', '', '', null, 'GET', '000', '35218933621456896', '/35218177046609920/35218933621456896/35219088680681472/', '000', '000', '2018-04-10 08:56:10', '33101303561322496', '公司管理员', '2018-04-10 09:00:50', '33101303561322496', '公司管理员');
INSERT INTO `t_privilege` VALUES ('35219153436540928', '切换职务接口', 'manager:fore.menus.switch-job', '', '', '', '', '', null, 'GET', '000', '35218933621456896', '/35218177046609920/35218933621456896/35219153436540928/', '000', '000', '2018-04-10 08:56:41', '33101303561322496', '公司管理员', '2018-04-10 09:01:02', '33101303561322496', '公司管理员');

-- ----------------------------
-- Table structure for t_project_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_project_dept`;
CREATE TABLE `t_project_dept` (
  `project_dept_id` bigint(20) NOT NULL,
  `project_dept_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `project_dept_code` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `com_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `office_location` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `office_postcode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `office_tel` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `office_fax` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `office_contact` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `main_org_id` bigint(20) DEFAULT NULL,
  `main_org_name` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`project_dept_id`),
  UNIQUE KEY `project_dept_id` (`project_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
  `role_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `role_code` varchar(255) COLLATE utf8_bin NOT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT '000',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `id_path` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `role_parent_id` (`parent_id`) USING BTREE,
  KEY `role_name` (`role_name`) USING BTREE,
  KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13889963376508929 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33105625764331521 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_bin NOT NULL,
  `password` varchar(30) COLLATE utf8_bin NOT NULL,
  `salt` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `realname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `nickname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cellphone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `portrait_url` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
  `weixin` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `is_locked` tinyint(3) unsigned zerofill DEFAULT '000',
  `last_login_ip` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(3) unsigned zerofill DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creater_id` bigint(20) DEFAULT NULL,
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `full_text` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=33101303561322497 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '12345678', null, '系统管理员', '系统管理员', 'admin@huatu.com', '13910326704', null, null, '1981-12-01', '男', '000', null, null, '000', '2017-10-21 10:18:17', null, null, null, null, null, '0', '1:admin:系统管理员:系统管理员:admin@huatu.com:13910326704:1981-12-01:男');
INSERT INTO `t_user` VALUES ('12948627418775552', 'aaa', '123456', null, 'aaa', 'aa', 'aa', 'aa', null, null, '2017-12-02', '（未知）', '000', null, null, '000', '2017-12-08 11:06:26', '1', null, '2018-03-10 12:07:33', '1', '系统管理员', '0', '12948627418775552:aaa:aaa:aa:aa:aa:2017-12-02 00:00:00:（未知）');
INSERT INTO `t_user` VALUES ('12949137737646080', 'aaaaa', 'aaaa', null, '真实姓名', '昵称', 'email@email.com', '100000000', null, null, null, '男', '000', null, null, '000', '2017-12-08 11:10:30', '1', '系统管理员', '2017-12-10 13:55:41', '1', '系统管理员', '0', '12949137737646080:aaaaa:真实姓名:昵称:email@email.com:100000000::男');
INSERT INTO `t_user` VALUES ('12952271639805952', 'cccc', 'aaaa', null, '真实姓名', '昵称', 'email', '10000000', null, null, null, '男', '000', null, null, '000', '2017-12-08 11:35:24', '1', '系统管理员', null, null, null, '0', null);
INSERT INTO `t_user` VALUES ('12953029835751424', 'ccccd', 'aaaa', null, '真实姓名', '昵称', 'email', '10000000', null, null, null, '男', '000', null, null, '000', '2017-12-08 11:41:25', '1', '系统管理员', '2017-12-10 13:55:27', '1', '系统管理员', '0', '12953029835751424:ccccd:真实姓名:昵称:email:10000000::男');
INSERT INTO `t_user` VALUES ('12954326488055808', 'ddddd', 'ddddd', null, 'eeee', 'fffff', 'ggggg', '11111111', null, null, '2017-12-16', '（未知）', '000', null, null, '000', '2017-12-08 11:51:44', '1', '系统管理员', null, null, null, '0', '12954326488055808:ddddd:eeee:fffff:ggggg:11111111:2017-12-16 00:00:00:（未知）');
INSERT INTO `t_user` VALUES ('12954379130765312', 'ddddde', 'ddddd', null, 'eeee', 'fffff', '', '11111111', null, null, null, '（未知）', '000', null, null, '000', '2017-12-08 11:52:09', '1', '系统管理员', null, null, null, '0', '12954379130765312:ddddde:eeee:fffff::11111111::（未知）');
INSERT INTO `t_user` VALUES ('12954786605301760', 'ddddddddddd', 'eeeeeeee', null, 'ffffffffff', 'ggggggggg', 'hhhhhhhh', 'iiiiiiiii', null, null, '2017-12-30', '男', '000', null, null, '000', '2017-12-08 11:55:23', '1', '系统管理员', null, null, null, '1', '12954786605301760:ddddddddddd:ffffffffff:ggggggggg:hhhhhhhh:iiiiiiiii:2017-12-30 00:00:00:男');
INSERT INTO `t_user` VALUES ('13345913443450880', 'aaabbbbbbbcccccc', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:43:47', '1', '系统管理员', '2017-12-16 19:34:41', '1', '系统管理员', '0', '13345913443450880:aaabbbbbbbcccccc:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13345931657216000', 'aaabbbbbbbccccccdd', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:43:56', '1', '系统管理员', '2017-12-10 15:44:28', '1', '系统管理员', '0', '13345931657216000:aaabbbbbbbccccccdd:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13345945253052416', 'aaabbbbbbbccccccddeee', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:44:02', '1', '系统管理员', '2017-12-10 15:44:39', '1', '系统管理员', '0', '13345945253052416:aaabbbbbbbccccccddeee:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13345962164486144', 'aaabbbbbbbccccccddeeefff', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '001', '2017-12-10 15:44:10', '1', '系统管理员', '2017-12-10 15:44:50', '1', '系统管理员', '0', '13345962164486144:aaabbbbbbbccccccddeeefff:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13346082838806528', 'user1', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '000', '2017-12-10 15:45:08', '1', '系统管理员', null, null, null, '0', '13346082838806528:user1:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13346100494729216', 'user2', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '000', '2017-12-10 15:45:16', '1', '系统管理员', null, null, null, '0', '13346100494729216:user2:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13346122449813504', 'user3', '1111111', null, 'aaaa', 'aaaaa', null, null, null, null, null, null, '000', null, null, '000', '2017-12-10 15:45:27', '1', '系统管理员', null, null, null, '0', '13346122449813504:user3:aaaa:aaaaa::::');
INSERT INTO `t_user` VALUES ('13855239046168576', 'user4', '1111111', null, 'aaaa', 'aaaa', 'aaaaa', 'aaaaa', null, null, '2017-12-07', '男', '000', null, null, '000', '2017-12-13 11:11:32', '1', '系统管理员', null, null, null, '0', '13855239046168576:user4:aaaa:aaaa:aaaaa:aaaaa:2017-12-07 00:00:00:男');
INSERT INTO `t_user` VALUES ('15722535150682112', '1111', '11111', null, '11111', '1111', '1111', '11111', null, null, '2017-12-31', '男', '000', null, null, '000', '2017-12-23 18:31:28', '1', '系统管理员', null, null, null, '0', '15722535150682112:1111:11111:1111:1111:11111:2017-12-31 00:00:00:男');
INSERT INTO `t_user` VALUES ('28960771325034496', 'ceshi', 'ceshi', null, '测试', '测试', 'ceshi@ceshi.ceshi', '111111', null, null, null, '（未知）', '000', null, null, '000', '2018-03-06 19:59:31', '1', '系统管理员', null, null, null, '1', '28960771325034496:ceshi:测试:测试:ceshi@ceshi.ceshi:111111::（未知）');
INSERT INTO `t_user` VALUES ('29615289918291968', 'htAdmin', '123456', null, '华图管理员', '管理员', 'ht@ht.com', '111111111', null, null, null, '（未知）', '000', null, null, '000', '2018-03-10 10:41:10', '1', '系统管理员', null, null, null, '1', '29615289918291968:htAdmin:华图管理员:管理员:ht@ht.com:111111111::（未知）');
INSERT INTO `t_user` VALUES ('29656955161149440', 'htEmp', '123456', null, '华图好员工', '华图好员工', 'htemp@ht.com', '111111', null, null, null, '男', '000', null, null, '000', '2018-03-10 16:12:18', '1', '系统管理员', null, null, null, '1', '29656955161149440:htEmp:华图好员工:华图好员工:htemp@ht.com:111111::男');
INSERT INTO `t_user` VALUES ('33101303561322496', 'comsAdmin', '123456', null, '公司管理员', '公司管理员', 'coms_admin@admin.com', '1111111111', null, null, null, '（未知）', '000', null, null, '000', '2018-03-29 16:25:31', '1', '系统管理员', null, null, null, '0', '33101303561322496:comsAdmin:公司管理员:公司管理员:coms_admin@admin.com:1111111111::（未知）');

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
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
  `creater_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `last_modifier_id` bigint(20) DEFAULT NULL,
  `last_modifier_name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_user_project_dept_position
-- ----------------------------
INSERT INTO `t_user_project_dept_position` VALUES ('2', '1', '1', '1', '000', null, '2017-10-09 17:06:52', null, null, null, null);
