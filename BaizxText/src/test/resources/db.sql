create table BL_ACCOUNT
(
  account_no   VARCHAR2(128),
  password     VARCHAR2(256),
  account_icon VARCHAR2(50)
)

create table BL_ACCOUNT_GROUP
(
  group_id   VARCHAR2(20),
  account_no VARCHAR2(128)
)

create table BL_GROUP
(
  group_id     VARCHAR2(20),
  group_name   VARCHAR2(64),
  group_father VARCHAR2(20),
  icon_cls     VARCHAR2(50)
)

create table BL_MENU_GROUP
(
  menu_id  VARCHAR2(20),
  group_id VARCHAR2(20)
)

create table BL_MENU
(
  menu_id        VARCHAR2(20),
  parent_menu_id VARCHAR2(20),
  menu_name_en   VARCHAR2(128),
  menu_name_ch   VARCHAR2(128),
  auth_flag      CHAR(1),
  menu_icon      VARCHAR2(50),
  menu_type      CHAR(1),
  menu_url       VARCHAR2(128)
)

insert into bl_group (group_id,group_name) values('0','system');

insert into BL_ACCOUNT (ACCOUNT_NO,PASSWORD) values('system','123456');