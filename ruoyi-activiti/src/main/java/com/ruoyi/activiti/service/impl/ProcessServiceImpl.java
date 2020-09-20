package com.ruoyi.activiti.service.impl;


import com.ruoyi.activiti.domain.ActivitiBaseEntity;
import com.ruoyi.activiti.domain.BizTodoItem;
import com.ruoyi.activiti.domain.HistoricActivity;

import com.ruoyi.activiti.service.IBizTodoItemService;
import com.ruoyi.activiti.service.IProcessService;
import com.ruoyi.common.core.domain.entity.SysUser;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;

import com.ruoyi.system.mapper.SysUserMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ProcessServiceImpl implements IProcessService {
    protected final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private IBizTodoItemService bizTodoItemService;

    @Override
    public ProcessInstance submitApply(String applyUserId, String businessKey, String itemName, String itemConent, String module, Map<String, Object> variables) {
        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(applyUserId);
        // 启动流程时设置业务 key
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(module, businessKey, variables);
        // 下一节点处理人待办事项
        bizTodoItemService.insertTodoItem(instance.getProcessInstanceId(), itemName, itemConent, module);
        return instance;
    }

    @Override
    public List<Task> findTodoTasks(String userId, String key) {
        List<Task> tasks = new ArrayList<Task>();
        // 根据当前人的ID查询
        List<Task> todoList = taskService
                .createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(userId)
                .list();
        // 根据当前人未签收的任务
        List<Task> unsignedTasks = taskService
                .createTaskQuery()
                .processDefinitionKey(key)
                .taskCandidateUser(userId)
                .list();
        // 合并
        tasks.addAll(todoList);
        tasks.addAll(unsignedTasks);
        return tasks;
    }

    @Override
    public List<HistoricTaskInstance> findDoneTasks(String userId, String key) {
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .processDefinitionKey(key)
                .taskAssignee(userId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        return list;
    }

    @Override
    public void complete(ActivitiBaseEntity activitiBaseEntity,String module) {
        Map<String, Object> variables =new HashMap<String, Object>();
        String comment = null;          // 批注
        boolean agree = true;
        try {
        for(Map.Entry<String, Object> entry : activitiBaseEntity.getParams().entrySet()){
            String parameterName = entry.getKey();
                if (parameterName.startsWith("p_")) {
                    // 参数结构：p_B_name，p为参数的前缀，B为类型，name为属性名称
                    String[] parameter = parameterName.split("_");
                    if (parameter.length == 3) {
                        String paramValue = (String) entry.getValue();;
                        Object value = paramValue;
                        if (parameter[1].equals("B")) {
                            value = BooleanUtils.toBoolean(paramValue);
                            agree = (boolean) value;
                        } else if (parameter[1].equals("DT")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            value = sdf.parse(paramValue);
                        } else if (parameter[1].equals("COM")) {
                            comment = paramValue;
                        }
                        variables.put(parameter[2], value);
                    }
                }
        }



            if (StringUtils.isNotEmpty(comment)) {
                identityService.setAuthenticatedUserId(SecurityUtils.getUsername());
                comment = agree ? "【同意】" + comment : "【拒绝】" + comment;
                taskService.addComment(activitiBaseEntity.getTaskId(), activitiBaseEntity.getInstanceId(), comment);
            }
            // 被委派人处理完成任务
            // p.s. 被委托的流程需要先 resolved 这个任务再提交。
            // 所以在 complete 之前需要先 resolved
            // resolveTask() 要在 claim() 之前，不然 act_hi_taskinst 表的 assignee 字段会为 null
            taskService.resolveTask(activitiBaseEntity.getTaskId(), variables);
            // 只有签收任务，act_hi_taskinst 表的 assignee 字段才不为 null
            taskService.claim(activitiBaseEntity.getTaskId(), SecurityUtils.getUsername());
            taskService.complete(activitiBaseEntity.getTaskId(), variables);

            // 更新待办事项状态
            BizTodoItem query = new BizTodoItem();
            query.setTaskId(activitiBaseEntity.getTaskId());
            // 考虑到候选用户组，会有多个 todoitem 办理同个 task
            List<BizTodoItem> updateList = CollectionUtils.isEmpty(bizTodoItemService.selectBizTodoItemList(query)) ? null : bizTodoItemService.selectBizTodoItemList(query);
            for (BizTodoItem update: updateList) {
                // 找到当前登录用户的 todoitem，置为已办
                if (update.getTodoUserId().equals(SecurityUtils.getUsername())) {
                    update.setIsView("1");
                    update.setIsHandle("1");
                    update.setHandleUserId(SecurityUtils.getUsername());
                    update.setHandleUserName(SecurityUtils.getNickName());
                    update.setHandleTime(DateUtils.getNowDate());
                    bizTodoItemService.updateBizTodoItem(update);
                } else {
                    bizTodoItemService.deleteBizTodoItemById(update.getId()); // 删除候选用户组其他 todoitem
                }
            }

            // 下一节点处理人待办事项
            bizTodoItemService.insertTodoItem(activitiBaseEntity.getInstanceId(),activitiBaseEntity.getTitle(),activitiBaseEntity.getReason(), module);
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{activitiBaseEntity.getTaskId(), variables, e});
        }
    }

    @Override
    public List<HistoricActivity> selectHistoryList(String processInstanceId, HistoricActivity historicActivity) {
//        PageDomain pageDomain = TableSupport.buildPageRequest();
//        Integer pageNum = pageDomain.getPageNum();
//        Integer pageSize = pageDomain.getPageSize();
        List<HistoricActivity> activityList = new ArrayList<>();
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery();
        if (StringUtils.isNotBlank(historicActivity.getAssignee())) {
            query.taskAssignee(historicActivity.getAssignee());
        }
        if (StringUtils.isNotBlank(historicActivity.getActivityName())) {
            query.activityName(historicActivity.getActivityName());
        }
        List<HistoricActivityInstance> list = query.processInstanceId(processInstanceId)
                .activityType("userTask")
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();
//                .listPage((pageNum - 1) * pageSize, pageNum * pageSize);
        list.forEach(instance -> {
            HistoricActivity activity = new HistoricActivity();
            BeanUtils.copyProperties(instance, activity);
            String taskId = instance.getTaskId();
            List<Comment> comment = taskService.getTaskComments(taskId, "comment");
            if (!CollectionUtils.isEmpty(comment)) {
                activity.setComment(comment.get(0).getFullMessage());
            }
            SysUser sysUser = userMapper.selectUserByUserName(instance.getAssignee());
            if (sysUser != null) {
                activity.setAssigneeName(sysUser.getUserName());
            }
            activityList.add(activity);
        });
        return activityList;
    }

    @Override
    public void delegate(String taskId, String fromUser, String delegateToUser) {
        taskService.delegateTask(taskId, delegateToUser);
        // 更新待办事项
//        BizTodoItem updateItem = bizTodoItemService.selectBizTodoItemByCondition(taskId, fromUser);
//        if (updateItem != null) {
//            SysUser todoUser = userMapper.selectUserByLoginName(delegateToUser);
//            updateItem.setTodoUserId(delegateToUser);
//            updateItem.setTodoUserName(todoUser.getUserName());
//            bizTodoItemService.updateBizTodoItem(updateItem);
//        }
    }

    @Override
    public void cancelApply(String instanceId, String deleteReason) {
        // 执行此方法后未审批的任务 act_ru_task 会被删除，流程历史 act_hi_taskinst 不会被删除，并且流程历史的状态为finished完成
        runtimeService.deleteProcessInstance(instanceId, deleteReason);
    }

    @Override
    public void suspendOrActiveApply(String instanceId, String suspendState) {
        if ("1".equals(suspendState)) {
            // 当流程实例被挂起时，无法通过下一个节点对应的任务id来继续这个流程实例。
            // 通过挂起某一特定的流程实例，可以终止当前的流程实例，而不影响到该流程定义的其他流程实例。
            // 激活之后可以继续该流程实例，不会对后续任务造成影响。
            // 直观变化：act_ru_task 的 SUSPENSION_STATE_ 为 2
            runtimeService.suspendProcessInstanceById(instanceId);
        } else if ("2".equals(suspendState)) {
            runtimeService.activateProcessInstanceById(instanceId);
        }
    }

    @Override
    public String findBusinessKeyByInstanceId(String instanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(instanceId)
                    .singleResult();
            return historicProcessInstance.getBusinessKey();
        } else {
            return processInstance.getBusinessKey();
        }
    }

}
