/*
 Navicat Premium Data Transfer

 Source Server         : HJC
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : finally_system02

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 04/02/2021 21:47:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表的唯一表示',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色的名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1307223787732750338 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会议室的唯一标识',
  `room_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室的名字',
  `room_seat_number` int(11) NOT NULL COMMENT '会议室容纳的人数',
  `room_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室的描述',
  `room_enabled` smallint(6) NOT NULL DEFAULT 1 COMMENT '会议室是否可用',
  `room_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室的图片',
  `room_location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室的位置',
  `room_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室类型名称',
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1298500182891159553 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for room_order
-- ----------------------------
DROP TABLE IF EXISTS `room_order`;
CREATE TABLE `room_order`  (
  `room_order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请会议室的唯一标识',
  `room_id` bigint(20) NOT NULL COMMENT '会议室的唯一标识（也就是你你申请哪个会议室）',
  `user_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '哪个用户申请的（学号或者工号）',
  `start_end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开始和结束时间的字符串描述',
  `activity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '要用会议室做什么活动的描述',
  `status` smallint(6) NOT NULL COMMENT '申请状态3代表决绝通过的历史订单，2代表申请通过的历史点单，0代表不通过，1代表通过，-1代表还没有处理',
  `pwd` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请的密码，由管理员填写',
  `reject_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '决绝申请的理由',
  `order_time` datetime(2) NOT NULL COMMENT '申请的时间',
  `authentic_time` datetime(2) NULL DEFAULT NULL COMMENT '处理的申请的时间',
  `over_time` bigint(6) NULL DEFAULT NULL COMMENT '在距=距离使用会议室多长时间不能修改会议室的申请',
  `time_slot` smallint(6) NOT NULL COMMENT '一个会议室一天有4个时段可以预约',
  `day` datetime(2) NOT NULL COMMENT '描述预约的会议室是哪天，只精确到天',
  `start_time` bigint(20) NOT NULL COMMENT '开始时间，用毫秒表示',
  `end_time` bigint(20) NOT NULL COMMENT '结束时间,用毫秒表示',
  `people_num` int(11) NOT NULL COMMENT '使用会议室的人数',
  PRIMARY KEY (`room_order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1294619104374374433 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for room_type
-- ----------------------------
DROP TABLE IF EXISTS `room_type`;
CREATE TABLE `room_type`  (
  `room_type_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '会议室类型表的唯一标识',
  `room_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室类型的名称',
  PRIMARY KEY (`room_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1302909078091100162 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '用户的唯一表示',
  `user_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号或者工号',
  `user_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '要使用MD5等加密密码，加密',
  `user_true_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户的真实姓名',
  `user_department` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是哪个院系的，那个专业',
  `user_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的邮箱，方便后来管理',
  `user_enabled` smallint(6) NOT NULL DEFAULT 0 COMMENT '学生毕业了，就不能再让他使用了',
  `user_register_time` datetime(0) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_lock
-- ----------------------------
DROP TABLE IF EXISTS `user_lock`;
CREATE TABLE `user_lock`  (
  `lock_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '锁定用户表的唯一标识',
  `user_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名（学号或者工号）',
  `lock_start_time` datetime(0) NOT NULL COMMENT '锁定的开始时间',
  `lock_end_time` datetime(0) NOT NULL COMMENT '锁定的结束时间',
  PRIMARY KEY (`lock_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '这个表主要是防止用户频繁登陆错误  频繁获取验证码等，超过限制就锁定一段时间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
  `log_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '用户登录日志表的唯一标识',
  `user_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '无名用户' COMMENT '用户名（学号或者工号）',
  `log_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录日志描述',
  `log_time` datetime(0) NOT NULL COMMENT '登录时间',
  `ip_address` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录的ip地址',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1307229893393666049 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名（学号或者工号）',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT '用户的角色名称',
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色表的唯一标识',
  PRIMARY KEY (`user_role_id`) USING BTREE,
  UNIQUE INDEX `UserRoleName`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1307223787590144002 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for add_room_order
-- ----------------------------
DROP PROCEDURE IF EXISTS `add_room_order`;
delimiter ;;
CREATE PROCEDURE `add_room_order`(in roomId bigint,in userName varchar(15),in timeSlot VARCHAR(2),in d datetime(2),in startEndTime varchar(255),in stau SMALLINT,in overTime BIGINT(20),in act varchar(255),in orderTime datetime(2),in startTime BIGINT(20) ,in endTime BIGINT(20),in peopleNum int)
  SQL SECURITY INVOKER
begin
    select count(*) into @isExist from room_order,room where room.room_id=room_order.room_id
		and room.room_enabled=1
		and room.room_id=roomId 
		and day=d 
		and time_slot=timeSlot 
		and room.room_id=roomId 
		and (`status`=1 or `status`=-1);
		select count(*) into @roomIsExist from room where room_id=roomId and room_enabled=1;
		if(@isExist>0)
		then
		select 0;
		elseif(@roomIsExist=0)
		then
		select 0;
		else
		insert into room_order(room_id,user_name,time_slot,day,start_end_time,`status`,over_time,activity,order_time,start_time,end_time,people_num)VALUES(roomId,userName,timeSlot,d,startEndTime,stau,overTime,act,orderTime,startTime,endTime,peopleNum);
		end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for modify_user_pwd
-- ----------------------------
DROP PROCEDURE IF EXISTS `modify_user_pwd`;
delimiter ;;
CREATE PROCEDURE `modify_user_pwd`(in userName VARCHAR(255) ,in  userPwd VARCHAR(255) ,in nowUserPwd VARCHAR(255))
  SQL SECURITY INVOKER
begin
		select count(*) into @nowPwdIsRight from user where user_name=userName and user_pwd=nowUserPwd;
		if(@nowPwdIsRight!=0)
		then
		update user set user_pwd=userPwd where user_name=userName;
		else 
		select -1;
		end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for register_user
-- ----------------------------
DROP PROCEDURE IF EXISTS `register_user`;
delimiter ;;
CREATE PROCEDURE `register_user`(in userName VARCHAR(255) ,in  userPwd VARCHAR(255) ,in userTrueName VARCHAR(255),
in  userDepartment VARCHAR(255),in userEmail  VARCHAR(255),in userRole varchar(255),in userRegisterTime datetime(2))
  SQL SECURITY INVOKER
begin
		
    SELECT COUNT(*) into @isExist  FROM `user` WHERE user_name=userName;
if(@isExist=0)
THEN
insert into `user`( user_name,user_pwd, user_true_name, user_department, user_email,user_enabled,user_register_time)
VALUES(userName,userPwd,userTrueName,userDepartment,userEmail,1,userRegisterTime);
INSERT into user_role(user_name,role_name) VALUES(userName,userRole );
ELSE
SELECT @isExist;
end if;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
