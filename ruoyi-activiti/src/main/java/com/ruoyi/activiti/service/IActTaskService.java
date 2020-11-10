package com.ruoyi.activiti.service;

import com.github.pagehelper.Page;
import com.ruoyi.activiti.domain.dto.ActWorkflowFormDataDTO;
import com.ruoyi.activiti.domain.dto.ActTaskDTO;
import com.ruoyi.common.core.page.PageDomain;

import java.text.ParseException;
import java.util.List;

public interface IActTaskService {
    public Page<ActTaskDTO> selectProcessDefinitionList(PageDomain pageDomain);
    public List<String>formDataShow(String taskID);
    public int formDataSave(String taskID, List<ActWorkflowFormDataDTO> awfs) throws ParseException;
}
