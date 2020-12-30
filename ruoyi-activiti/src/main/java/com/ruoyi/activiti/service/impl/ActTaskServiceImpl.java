

package com.ruoyi.activiti.service.impl;

import com.github.pagehelper.Page;
import com.ruoyi.activiti.domain.ActWorkflowFormData;
import com.ruoyi.activiti.domain.dto.ActWorkflowFormDataDTO;
import com.ruoyi.activiti.domain.dto.ActTaskDTO;
import com.ruoyi.activiti.service.IActTaskService;
import com.ruoyi.activiti.service.IActWorkflowFormDataService;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.system.domain.SysCustomForm;
import com.ruoyi.system.service.ISysCustomFormService;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActTaskServiceImpl implements IActTaskService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IActWorkflowFormDataService actWorkflowFormDataService;
    @Autowired
    private ISysCustomFormService iSysCustomFormService;


    @Override
    public Page<ActTaskDTO> selectProcessDefinitionList(PageDomain pageDomain) {
        Page<ActTaskDTO> list = new Page<ActTaskDTO>();
        org.activiti.api.runtime.shared.query.Page<Task> pageTasks = taskRuntime.tasks(Pageable.of((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize()));
        List<Task> tasks = pageTasks.getContent();
        int totalItems = pageTasks.getTotalItems();
        list.setTotal(totalItems);
        if (totalItems != 0) {
            Set<String> processInstanceIdIds = tasks.parallelStream().map(t -> t.getProcessInstanceId()).collect(Collectors.toSet());
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIdIds).list();
            List<ActTaskDTO> actTaskDTOS = tasks.stream()
                    .map(t -> new ActTaskDTO(t, processInstanceList.parallelStream().filter(pi -> t.getProcessInstanceId().equals(pi.getId())).findAny().get()))
                    .collect(Collectors.toList());
            list.addAll(actTaskDTOS);

        }
        return list;
    }

    @Override
    public Map<String, List<String>> formDataShow(String taskID) {
        Map<String, List<String>> map = new HashMap<>();

        Task task = taskRuntime.task(taskID);
        SysCustomForm customForm = iSysCustomFormService.selectSysCustomFormByKey(task.getFormKey());
        if (customForm != null){
            map.put(task.getFormKey(), Collections.singletonList(customForm.getFormJson()));
        }else {
            map.put(task.getFormKey(), null);
        }
        return map;

        /*
        FormProperty_0ueitp2--__!!类型--__!!名称--__!!是否参数--__!!默认值
        例子：
        FormProperty_0lovri0--__!!string--__!!姓名--__!!f--__!!同意!!__--驳回
        FormProperty_1iu6onu--__!!int--__!!年龄--__!!s

        默认值：无、字符常量、FormProperty_开头定义过的控件ID
        是否参数：f为不是参数，s是字符，t是时间(不需要int，因为这里int等价于string)
        注：类型是可以获取到的，但是为了统一配置原则，都配置到
        */

        /*
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        //注意!!!!!!!!:表单Key必须要任务编号一模一样，因为参数需要任务key，但是无法获取，只能获取表单key“task.getFormKey()”当做任务key
        UserTask userTask = (UserTask) bpmnModel.getFlowElement(task.getId());
        if (userTask == null) {
            return null;
        }

        // 加载表单数据
        SysCustomForm customForm = iSysCustomFormService.selectSysCustomFormByKey(userTask.getFormKey());
        System.out.println("userTask.getFormKey() >>>" + userTask.getFormKey());
        System.out.println("customForm >>>" + customForm);
        if (customForm != null){
            map.put(userTask.getFormKey(), Collections.singletonList(customForm.getFormJson()));
        }else {
            List<FormProperty> formProperties = userTask.getFormProperties();
            List<String> collect = formProperties.stream().map(fp -> fp.getId()).collect(Collectors.toList());
            map.put(userTask.getFormKey(), collect);
        }
        return map;*/
    }

    @Override
    public int formDataSave(String taskID, Map<String, Object> params) throws ParseException {
        Task task = taskRuntime.task(taskID);
        //ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();

        if (task.getAssignee() == null) {
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        }
        if (!CollectionUtils.isEmpty(params)) {
            //带参数完成任务
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                    .withVariables(params)
                    .build());
        } else {
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                    .build());
        }


        //TODO 写入数据库
        return 1;
//        return actWorkflowFormDataService.insertActWorkflowFormDatas(acwfds);
    }
}
