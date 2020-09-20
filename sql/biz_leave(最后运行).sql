

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_leave
-- ----------------------------
DROP TABLE IF EXISTS `biz_leave`;
CREATE TABLE `biz_leave`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假类型',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `leave_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `leave_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `total_time` bigint(11) NULL DEFAULT NULL COMMENT '请假时长，单位秒',
  `instance_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例ID',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `apply_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `reality_start_time` datetime(0) NULL DEFAULT NULL COMMENT '实际开始时间',
  `reality_end_time` datetime(0) NULL DEFAULT NULL COMMENT '实际结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- 添加请假流程菜单
-- ----------------------------
INSERT INTO sys_menu ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('请假流程', 0, 5, 'leave', NULL, 1, 'M', '0', '0', NULL, 'guide', 'admin', '2020-09-18 10:49:58', '', NULL, '');


SELECT @parentId := LAST_INSERT_ID();

INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('请假流程',  @parentId, 1, 'leave', 'workflow/leave/index', 1, 'C', '0', '0', 'workflow:leave', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2020-09-20 17:34:01', '请假流程菜单');

SELECT @menuId1 := LAST_INSERT_ID();

INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('我的待办',  @parentId, 2, 'leaveTodo', 'workflow/leaveTodo/index', 1, 'C', '0', '0', 'workflow:leave', '#', 'admin', '2020-09-20 00:14:44', 'admin', '2020-09-20 00:32:15', '');

SELECT @menuId2 := LAST_INSERT_ID();

INSERT INTO sys_menu(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('我的已办',  @parentId, 3, 'leaveDone', 'workflow/leaveDone/index', 1, 'C', '0', '0', 'workflow:leave', '#', 'admin', '2020-09-20 00:16:24', 'admin', '2020-09-20 00:32:21', '');

SELECT @menuId3 := LAST_INSERT_ID();


-- ----------------------------
-- 添加岗位
-- ----------------------------
INSERT INTO sys_post(`post_code`, `post_name`, `post_sort`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('deptLeader', '部门领导', 5, '0', 'admin', '2020-09-18 10:59:39', '', NULL, NULL);
SELECT @postId := LAST_INSERT_ID();

-- ----------------------------
-- 添加角色
-- ----------------------------
INSERT INTO sys_role(`role_name`, `role_key`, `role_sort`, `data_scope`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('请假流程', 'leave', 3, '1', '0', '0', 'admin', '2020-09-15 21:45:24', 'admin', '2020-09-20 17:34:22', NULL);
SELECT @roleId := LAST_INSERT_ID();
-- ----------------------------
-- 添加用户
-- ----------------------------
INSERT INTO sys_user(`dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (105, 'chengxy', '程序猿', '00', '5678@126.com', '18612345678', '0', '', '$2a$10$sGpjUm5Dcr/R2hF2vB.rVesrAmivSImOMRvVG7FftQmUa.beLaDd6', '0', '0', '', NULL, 'admin', '2020-09-15 21:48:35', 'admin', '2020-09-20 00:20:27', NULL);
SELECT @userId1 := LAST_INSERT_ID();
INSERT INTO sys_user(`dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (105, 'axianlu', '一直闲鹿', '00', '5677@126.com', '18612345677', '0', '', '$2a$10$NnKYtA5fnfh.lJYFHYg1f.Gpxjo.ciXhNmJnJ5xxOvJ6GhW/LoE6S', '0', '0', '', NULL, 'admin', '2020-09-15 21:51:03', 'admin', '2020-09-20 00:20:34', NULL);
SELECT @userId2 := LAST_INSERT_ID();
INSERT INTO sys_user(`dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (105, 'rensm', '人事喵', '00', '5676@126.com', '18612345676', '1', '', '$2a$10$rYsRNoUDmA41RTOV6vsKr.UMNnvPspV92zX.A2ZbV78vokHwKWMiW', '0', '0', '', NULL, 'admin', '2020-09-15 21:51:59', 'admin', '2020-09-20 00:20:43', NULL);
SELECT @userId3 := LAST_INSERT_ID();



INSERT INTO sys_user_post(`user_id`, `post_id`) VALUES (@userId1, 4);
INSERT INTO sys_user_post(`user_id`, `post_id`) VALUES (@userId2, @postId);
INSERT INTO sys_user_post(`user_id`, `post_id`) VALUES (@userId3, 3);


INSERT INTO sys_user_role(`user_id`, `role_id`) VALUES (@userId1, @roleId);
INSERT INTO sys_user_role(`user_id`, `role_id`) VALUES (@userId2, @roleId);
INSERT INTO sys_user_role(`user_id`, `role_id`) VALUES (@userId3, @roleId);

INSERT INTO `test`.`sys_role_menu`(`role_id`, `menu_id`) VALUES (@roleId, @parentId);
INSERT INTO `test`.`sys_role_menu`(`role_id`, `menu_id`) VALUES (@roleId, @menuId1);
INSERT INTO `test`.`sys_role_menu`(`role_id`, `menu_id`) VALUES (@roleId, @menuId2);
INSERT INTO `test`.`sys_role_menu`(`role_id`, `menu_id`) VALUES (@roleId, @menuId3);




















