package com.ruoyi.activiti.service;

import com.github.pagehelper.Page;
import com.ruoyi.activiti.domain.dto.ActFormDataSaveDTO;
import com.ruoyi.activiti.domain.dto.ActTaskDTO;
import com.ruoyi.common.core.page.PageDomain;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IActTaskService {
    public Page<ActTaskDTO> selectProcessDefinitionList(PageDomain pageDomain);
    public Map<String, List<String>> formDataShow(String taskID);
    public int formDataSave(String taskID, ActFormDataSaveDTO params) throws ParseException;
}
