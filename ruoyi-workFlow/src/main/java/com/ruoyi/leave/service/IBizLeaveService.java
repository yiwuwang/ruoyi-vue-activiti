package com.ruoyi.leave.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.leave.domain.BizLeave;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 请假流程Service接口
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
public interface IBizLeaveService 
{
    /**
     * 查询请假流程
     * 
     * @param id 请假流程ID
     * @return 请假流程
     */
    public BizLeave selectBizLeaveById(Long id);

    /**
     * 查询请假流程列表
     * 
     * @param bizLeave 请假流程
     * @return 请假流程集合
     */
    public List<BizLeave> selectBizLeaveList(BizLeave bizLeave);

    /**
     * 新增请假流程
     * 
     * @param bizLeave 请假流程
     * @return 结果
     */
    public int insertBizLeave(BizLeave bizLeave);

    /**
     * 修改请假流程
     * 
     * @param bizLeave 请假流程
     * @return 结果
     */
    public int updateBizLeave(BizLeave bizLeave);

    /**
     * 批量删除请假流程
     * 
     * @param ids 需要删除的请假流程ID
     * @return 结果
     */
    public int deleteBizLeaveByIds(Long[] ids);

    /**
     * 删除请假流程信息
     * 
     * @param id 请假流程ID
     * @return 结果
     */
    public int deleteBizLeaveById(Long id);
    /**
     * 启动流程
     * @param entity
     * @param applyUserId
     * @return
     */
    ProcessInstance submitApply(BizLeave entity, String applyUserId, String key, Map<String, Object> variables);

    /**
     * 查询我的待办列表
     * @param userId
     * @return
     */
    List<BizLeave> findTodoTasks(BizLeave leave, String userId);
    /**
     * 查询我的已办列表
     * @param userId
     * @return
     */
    List<BizLeave> findDoneTasks(BizLeave bizLeaveVo, String userId);
}
