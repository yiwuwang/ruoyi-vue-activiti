package com.ruoyi.activiti.service.impl;

import com.ruoyi.activiti.domain.BizTodoItem;
import com.ruoyi.activiti.mapper.BizTodoItemMapper;
import com.ruoyi.activiti.service.IBizTodoItemService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 待办事项Service业务层处理
 *
 * @author Xianlu Tech
 * @date 2019-11-08
 */
@Service
@Transactional
public class BizTodoItemServiceImpl implements IBizTodoItemService {
    @Autowired
    private BizTodoItemMapper bizTodoItemMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private TaskService taskService;

    /**
     * 查询待办事项
     *
     * @param id 待办事项ID
     * @return 待办事项
     */
    @Override
    public BizTodoItem selectBizTodoItemById(Long id) {
        return bizTodoItemMapper.selectBizTodoItemById(id);
    }

    /**
     * 查询待办事项列表
     *
     * @param bizTodoItem 待办事项
     * @return 待办事项
     */
    @Override
    public List<BizTodoItem> selectBizTodoItemList(BizTodoItem bizTodoItem) {
        return bizTodoItemMapper.selectBizTodoItemList(bizTodoItem);
    }

    /**
     * 新增待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    @Override
    public int insertBizTodoItem(BizTodoItem bizTodoItem) {
        return bizTodoItemMapper.insertBizTodoItem(bizTodoItem);
    }

    /**
     * 修改待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    @Override
    public int updateBizTodoItem(BizTodoItem bizTodoItem) {
        return bizTodoItemMapper.updateBizTodoItem(bizTodoItem);
    }

    /**
     * 删除待办事项对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizTodoItemByIds(String ids) {
        return bizTodoItemMapper.deleteBizTodoItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除待办事项信息
     *
     * @param id 待办事项ID
     * @return 结果
     */
    @Override
    public int deleteBizTodoItemById(Long id) {
        return bizTodoItemMapper.deleteBizTodoItemById(id);
    }

    @Override
    public int insertTodoItem(String instanceId, String itemName, String itemContent, String module) {
        BizTodoItem todoItem = new BizTodoItem();
        todoItem.setItemName(itemName);
        todoItem.setItemContent(itemContent);
        todoItem.setIsView("0");
        todoItem.setIsHandle("0");
        todoItem.setModule(module);
        todoItem.setTodoTime(DateUtils.getNowDate());
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).active().list();
        int counter = 0;
        for (Task task: taskList) {

            // todoitem 去重
            BizTodoItem bizTodoItem = bizTodoItemMapper.selectTodoItemByTaskId(task.getId());
            if (bizTodoItem != null) continue;

            BizTodoItem newItem = new BizTodoItem();
            BeanUtils.copyProperties(todoItem, newItem);
            newItem.setInstanceId(instanceId);
            newItem.setTaskId(task.getId());
            newItem.setTaskName("task" + task.getTaskDefinitionKey().substring(0, 1).toUpperCase() + task.getTaskDefinitionKey().substring(1));
            newItem.setNodeName(task.getName());
            String assignee = task.getAssignee();
            if (StringUtils.isNotBlank(assignee)) {
                newItem.setTodoUserId(assignee);
                SysUser user = userMapper.selectUserByUserName(assignee);
                newItem.setTodoUserName(user.getNickName());
                bizTodoItemMapper.insertBizTodoItem(newItem);
                counter++;
            } else {
                // 查询候选用户组
                List<String> todoUserIdList = bizTodoItemMapper.selectTodoUserListByTaskId(task.getId());
                if (!CollectionUtils.isEmpty(todoUserIdList)) {
                    for (String todoUserId: todoUserIdList) {
                        SysUser todoUser = userMapper.selectUserByUserName(todoUserId);
                        newItem.setTodoUserId(todoUser.getUserName());
                        newItem.setTodoUserName(todoUser.getNickName());
                        bizTodoItemMapper.insertBizTodoItem(newItem);
                        counter++;
                    }
                } else {
                    // 查询候选用户
                    String todoUserId = bizTodoItemMapper.selectTodoUserByTaskId(task.getId());
                    SysUser todoUser = userMapper.selectUserByUserName(todoUserId);
                    newItem.setTodoUserId(todoUser.getUserName());
                    newItem.setTodoUserName(todoUser.getNickName());
                    bizTodoItemMapper.insertBizTodoItem(newItem);
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public BizTodoItem selectBizTodoItemByCondition(String taskId, String todoUserId) {
        return bizTodoItemMapper.selectTodoItemByCondition(taskId, todoUserId);
    }
}
