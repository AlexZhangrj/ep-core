SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_group;
DROP TABLE IF EXISTS t_identity;
DROP TABLE IF EXISTS t_identity_role;
DROP TABLE IF EXISTS t_position;
DROP TABLE IF EXISTS t_privilege;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_role_privilege;
DROP TABLE IF EXISTS t_user;




/* Create Tables */

CREATE TABLE t_group
(
	group_id varchar(30) NOT NULL,
	group_name varchar(255),
	group_code varchar(200),
	parent_id varchar(30),
	id_path varchar(1024),
	is_locked tinyint unsigned zerofill,
	is_deleted tinyint zerofill,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (group_id)
);


CREATE TABLE t_identity
(
	identity_id varchar(30) NOT NULL,
	user_id varchar(30),
	group_id varchar(30),
	position_id varchar(30),
	is_deleted tinyint zerofill,
	is_locked tinyint zerofill,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (identity_id)
);


CREATE TABLE t_identity_role
(
	id varchar(30) NOT NULL,
	identity_id varchar(30),
	group_id varchar(30),
	user_id varbinary(30),
	role_id varchar(30),
	is_deleted tinyint zerofill,
	is_locked tinyint unsigned zerofill,
	creater_id varchar(30),
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE t_position
(
	position_id varchar(30) NOT NULL,
	position_name varchar(255),
	position_code varchar(255),
	group_id varchar(30),
	is_deleted tinyint zerofill,
	is_locked tinyint zerofill,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (position_id)
);


CREATE TABLE t_privilege
(
	privilege_id varchar(30) NOT NULL,
	privilege_name varchar(255) NOT NULL,
	privilege_code varchar(200),
	simple_url varchar(2038),
	icon_class varchar(40),
	icon_type varchar(40),
	is_menu tinyint zerofill,
	collapse varchar(40),
	sort_value decimal(19,4),
	request_method varchar(40),
	parent_id varchar(30),
	id_path varchar(1024),
	is_locked tinyint zerofill DEFAULT 0,
	is_deleted tinyint zerofill DEFAULT 0,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (privilege_id)
);


CREATE TABLE t_role
(
	role_id varchar(30) NOT NULL,
	role_name varchar(255) NOT NULL,
	role_code varchar(255) NOT NULL,
	group_id bigint,
	is_locked tinyint zerofill DEFAULT 0,
	is_deleted tinyint zerofill DEFAULT 0,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (role_id)
);


CREATE TABLE t_role_privilege
(
	id varchar(30) NOT NULL,
	role_id varchar(30),
	privilege_id varchar(30),
	is_deleted tinyint zerofill,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (id),
	UNIQUE (role_id, privilege_id)
);


CREATE TABLE t_user
(
	user_id varchar(30) NOT NULL,
	username varchar(255) NOT NULL,
	password varchar(128) NOT NULL,
	salt varchar(10),
	realname varchar(255),
	nickname varchar(255),
	email varchar(255),
	cellphone varchar(20),
	portrait_url varchar(2048),
	weixin varchar(100),
	birthday date,
	gender varchar(10),
	is_locked tinyint zerofill DEFAULT 0,
	last_login_ip varchar(128),
	last_login_time datetime,
	is_deleted tinyint zerofill,
	created_time datetime DEFAULT NOW(), SYSDATE(),
	creater_id varchar(30),
	creater_name varchar(255),
	last_modified_time datetime,
	last_modifier_id varchar(30),
	last_modifier_name varchar(255),
	PRIMARY KEY (user_id),
	UNIQUE (username)
);



