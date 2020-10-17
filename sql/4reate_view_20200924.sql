-- ----------------------------
-- View structure for act_id_group
-- ----------------------------
DROP VIEW IF EXISTS `ACT_ID_GROUP`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ACT_ID_GROUP` AS select `r`.`post_code` AS `ID_`,NULL AS `REV_`,`r`.`post_name` AS `NAME_`,'assignment' AS `TYPE_` from `sys_post` `r` ;

-- ----------------------------
-- View structure for act_id_membership
-- ----------------------------
DROP VIEW IF EXISTS `ACT_ID_MEMBERSHIP`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ACT_ID_MEMBERSHIP` AS select (select `u`.`user_name` from `sys_user` `u` where (`u`.`user_id` = `ur`.`user_id`)) AS `USER_ID_`,(select `r`.`post_code` from `sys_post` `r` where (`r`.`post_id` = `ur`.`post_id`)) AS `GROUP_ID_` from `sys_user_post` `ur` ;

-- ----------------------------
-- View structure for act_id_user
-- ----------------------------
DROP VIEW IF EXISTS `ACT_ID_USER`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ACT_ID_USER` AS select `u`.`user_name` AS `ID_`,0 AS `REV_`,`u`.`user_name` AS `FIRST_`,'' AS `LAST_`,`u`.`email` AS `EMAIL_`,`u`.`password` AS `PWD_`,'' AS `PICTURE_ID_` from `sys_user` `u` ;