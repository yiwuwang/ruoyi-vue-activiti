package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.ActivitiBaseEntity;
import com.ruoyi.activiti.domain.HistoricActivity;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IProcessService {

    /**
     * 查询审批历史列表
     * @param processInstanceId
     * @param historicActivity
     * @return
     */
    List<HistoricActivity> selectHistoryList(String processInstanceId, HistoricActivity historicActivity);

    /**
     * 提交申请
     * @param applyUserId 申请人
     * @param businessKey 业务表 id
     * @param key 流程定义 key
     * @param variables 流程变量
     * @return
     */
    ProcessInstance submitApply(String applyUserId, String businessKey, String itemName, String itemConent, String key, Map<String, Object> variables);

    List<Task> findTodoTasks(String userId, String key);

    List<HistoricTaskInstance> findDoneTasks(String userId, String key);

    void complete(ActivitiBaseEntity activitiBaseEntity, String module);

    /**
     * 委托任务
     * @param taskId
     * @param delegateToUser
     */
    void delegate(String taskId, String fromUser, String delegateToUser);

    void cancelApply(String instanceId, String deleteReason);

    void suspendOrActiveApply(String instanceId, String suspendState);

    String findBusinessKeyByInstanceId(String instanceId);
}
