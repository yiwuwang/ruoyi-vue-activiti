package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.domain.BizTodoItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 待办事项Mapper接口
 *
 * @author Xianlu Tech
 * @date 2019-11-08
 */
public interface BizTodoItemMapper {
    /**
     * 查询待办事项
     *
     * @param id 待办事项ID
     * @return 待办事项
     */
    public BizTodoItem selectBizTodoItemById(Long id);

    /**
     * 查询待办事项列表
     *
     * @param bizTodoItem 待办事项
     * @return 待办事项集合
     */
    public List<BizTodoItem> selectBizTodoItemList(BizTodoItem bizTodoItem);

    /**
     * 新增待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    public int insertBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 修改待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    public int updateBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 删除待办事项
     *
     * @param id 待办事项ID
     * @return 结果
     */
    public int deleteBizTodoItemById(Long id);

    /**
     * 批量删除待办事项
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizTodoItemByIds(String[] ids);

    @Select("SELECT * FROM BIZ_TODO_ITEM WHERE TASK_ID = #{taskId}")
    BizTodoItem selectTodoItemByTaskId(@Param(value = "taskId") String taskId);

    @Select("SELECT USER_ID_ FROM ACT_ID_MEMBERSHIP WHERE GROUP_ID_ = (SELECT GROUP_ID_ FROM ACT_RU_IDENTITYLINK WHERE TASK_ID_ = #{taskId})")
    List<String> selectTodoUserListByTaskId(@Param(value = "taskId") String taskId);

    @Select("SELECT * FROM BIZ_TODO_ITEM WHERE TASK_ID = #{taskId} AND TODO_USER_ID = #{todoUserId}")
    BizTodoItem selectTodoItemByCondition(@Param(value = "taskId") String taskId, @Param(value = "todoUserId") String todoUserId);

    @Select("SELECT USER_ID_ FROM ACT_ID_MEMBERSHIP WHERE USER_ID_ = (SELECT USER_ID_ FROM ACT_RU_IDENTITYLINK WHERE TASK_ID_ = #{taskId})")
    String selectTodoUserByTaskId(String id);
}
