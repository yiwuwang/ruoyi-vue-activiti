insert into sys_menu values('5', '流程菜单', '0', '4', 'activiti', null, 1, 0,'M', '0', '0', 'activiti:modeler:list', 'cascader', 'admin', '2020-09-13 21:58:54', '', NULL, '');
-- activiti模块

insert into sys_menu values(1900, '流程模型', 5,1, 'modeler', 'activiti/modeler/index', 1,0, 'C', '0', '0', 'activiti:modeler', '#', 'admin', '2020-09-13 21:59:42', 'admin', '2020-09-14 10:39:20', '');
insert into sys_menu values(1901, '流程定义', 5, 2, 'definition', 'activiti/definition/index', 1,0, 'C', '0', '0', 'activiti:modeler', '#', 'admin', '2020-09-14 23:09:31', '', NULL, '');
insert into sys_menu values(1902, '流程用户组', 5, 3, 'actIdGroup', 'activiti/actIdGroup/index', 1,0, 'C', '0', '0', 'activiti:actIdGroup', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2020-09-15 22:22:39', '流程用户组菜单');
insert into sys_menu values(1903, '流程用户', 5, 4,'actIdUser', 'activiti/actIdUser/index', 1,0, 'C', '0', '0', 'activiti:actIdUser', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '流程用户菜单');
-- ----------------------------


-- ----------------------------
-- Table structure for biz_todo_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_todo_item`;
CREATE TABLE `biz_todo_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `item_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事项标题',
  `item_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事项内容',
  `module` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称 (必须以 uri 一致)',
  `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务 ID',
  `instance_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例 ID',
  `task_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称 (必须以表单页面名称一致)',
  `node_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `is_view` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否查看 default 0 (0 否 1 是)',
  `is_handle` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否处理 default 0 (0 否 1 是)',
  `todo_user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待办人 ID',
  `todo_user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '待办人名称',
  `handle_user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人 ID',
  `handle_user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `todo_time` datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '待办事项表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
