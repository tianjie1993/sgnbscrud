/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.24 : Database - ms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `ms`;

/*Table structure for table `attachment` */

DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orginname` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '文件原始名称',
  `filename` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '文件名称',
  `path` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '文件访问路径',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '上传人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `attachment` */

/*Table structure for table `fieldcheck` */

DROP TABLE IF EXISTS `fieldcheck`;

CREATE TABLE `fieldcheck` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tablename` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '表名',
  `fieldname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '字段名称',
  `eums` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '枚举项键值对。逗号隔开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `fieldcheck` */

insert  into `fieldcheck`(`id`,`tablename`,`fieldname`,`eums`) values (1,'SYS_MENU','ISSHOW','1=默认显示,2=需要权限'),(2,'SYS_ACTION','CANBATCH','1=支持,2=不支持'),(3,'SYS_ACTION','TYPE','1=跳转到页,2=弹窗,3=ajax请求,4=跳转到新页'),(4,'SYS_ACTION','ISSHOW','1=默认显示,2=需要权限'),(5,'SYS_ACTION','NEEDID','1=需要,2=不需要');

/*Table structure for table `sys_action` */

DROP TABLE IF EXISTS `sys_action`;

CREATE TABLE `sys_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '操作请求地址',
  `canbatch` int(11) DEFAULT NULL COMMENT '是否支持批量操作1.是。2不是',
  `btnclass` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '按钮样式',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '按钮名称',
  `menu_id` int(11) DEFAULT NULL COMMENT '所属菜单',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `type` int(11) DEFAULT NULL COMMENT '1,弹窗。2跳转到页。3.ajax请求。4.跳转到空白页',
  `isshow` int(11) DEFAULT NULL COMMENT '1.默认显示，2,需要权限',
  `winxy` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '弹出框xy值',
  `permission` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT 'shiro权限字符串',
  `needid` int(11) DEFAULT NULL COMMENT '是否需要id.1.需要。2不需要',
  `menuname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所属菜单名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_action` */

insert  into `sys_action`(`id`,`url`,`canbatch`,`btnclass`,`name`,`menu_id`,`sort`,`type`,`isshow`,`winxy`,`permission`,`needid`,`menuname`) values (10,'/crud/tosave/sys_user',1,'xe654;','新增',10,1,2,1,'50%,50%','',2,'用户管理'),(11,'/crud/tosave/sys_user',2,'xe642;','编辑',10,2,2,1,'600px,600px','',1,'用户管理'),(12,'/crud/physicald/sys_user',1,'xe640;','删除',10,3,3,1,'','',1,'用户管理'),(13,'/user/toallotrole',2,'xe613;','分配角色',10,4,2,1,'500px,500px','user:allotrole',1,'用户管理'),(17,'/crud/tosave/sys_role',1,'xe654;','新增',11,1,2,1,'500px,500px','',2,'角色管理'),(18,'/crud/tosave/sys_role',2,'xe642;','编辑',11,2,2,1,'500px,500px','',1,'角色管理'),(19,'/crud/physicald/sys_role',1,'xe642;','删除',11,3,3,1,'','',1,'角色管理'),(20,'/role/toallotmenu',2,'xe62e;','分配菜单',11,4,2,1,'500px,500px','role:allotmenu',1,'角色管理'),(21,'/role/toallotbtn',1,'xe631;','分配操作按钮',11,5,2,1,'500px,500px','role:allotbtn',1,'角色管理');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `actiondesc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '操作描述',
  `actiontime` datetime DEFAULT NULL COMMENT '操作时间',
  `sonactionids` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '子级操作',
  `type` int(11) DEFAULT NULL COMMENT '1主体操作。2.子级操作',
  `result` int(11) DEFAULT NULL COMMENT '1成功。2失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`user_id`,`actiondesc`,`actiontime`,`sonactionids`,`type`,`result`) values (1,'7e8d9c560c714897a6477bfdf51507b7','田杰 新增或编辑用户','2018-03-11 01:56:56',NULL,NULL,NULL),(2,'7e8d9c560c714897a6477bfdf51507b7','田杰 新增或编辑用户','2018-03-11 01:58:33',NULL,NULL,NULL),(3,'7e8d9c560c714897a6477bfdf51507b7','田杰 新增或修改角色','2018-03-12 12:47:39',NULL,NULL,NULL),(4,'7e8d9c560c714897a6477bfdf51507b7','田杰 角色分配菜单','2018-03-21 14:54:03',NULL,NULL,NULL);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单地址',
  `parentid` int(50) DEFAULT NULL COMMENT '父菜单id',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `icon` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单样式',
  `isshow` int(11) DEFAULT NULL COMMENT '1默认展示2需要权限',
  `fixedprams` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '固定参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`name`,`url`,`parentid`,`sort`,`icon`,`isshow`,`fixedprams`) values (1,'根菜单','',0,0,'',NULL,NULL),(9,'系统管理','',1,1,'xe614;',2,''),(10,'用户管理','/sysuser/list',9,1,'xe612;',1,''),(11,'角色管理','/sysrole/list',9,2,'xe613;',2,''),(12,'系统日志','/syslog/list',9,3,'xe63c;',2,'');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `menu_ids` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '拥有菜单ids',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `roledesc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `action_ids` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '操作按钮ids',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`menu_ids`,`createtime`,`roledesc`,`action_ids`) values ('1','超管','9,10,11,12','2017-09-19 13:54:22','这是超管','3');

/*Table structure for table `sys_selectky` */

DROP TABLE IF EXISTS `sys_selectky`;

CREATE TABLE `sys_selectky` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `qsql` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '查询sql',
  `fields` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '展示列字段',
  `fieldnames` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '展示列名称',
  `keyto` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '键对应',
  `valueto` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '值对应',
  `searchinfo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '搜索信息',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_selectky` */

insert  into `sys_selectky`(`id`,`qsql`,`fields`,`fieldnames`,`keyto`,`valueto`,`searchinfo`,`remark`) values (1,'findSysMenuList','name,url','菜单名称,菜单地址','id,menuId','name,menuname','name:菜单名称','功能按钮选择菜单');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `login` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录账号',
  `password` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `role_ids` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色ids',
  `status` int(11) DEFAULT NULL COMMENT '1.正常。99删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`name`,`login`,`password`,`createtime`,`role_ids`,`status`) values ('7e8d9c560c714897a6477bfdf51507b7','田杰','admin','123','2017-09-06 15:49:32','1',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
