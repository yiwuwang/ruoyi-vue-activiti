package com.ruoyi.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.activiti.domain.ActWorkflowFormData;
import com.ruoyi.activiti.domain.dto.HistoryFormDataDTO;
import com.ruoyi.activiti.domain.dto.HistoryDataDTO;
import com.ruoyi.activiti.service.IActWorkflowFormDataService;
import com.ruoyi.activiti.service.IFormHistoryDataService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormHistoryDataServiceImpl implements IFormHistoryDataService {
    @Autowired
    private IActWorkflowFormDataService actWorkflowFormDataService;
    @Autowired
    private RuntimeService runtimeService;



    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<HistoryDataDTO> historyDataShow(String businessKey) {
        List<HistoryDataDTO> returnHistoryFromDataDTOS = new ArrayList<>();
        List<ActWorkflowFormData> ActWorkflowFormData = actWorkflowFormDataService.selectActWorkflowFormDataByBusinessKey(businessKey);
        Map<String, List<ActWorkflowFormData>> collect = ActWorkflowFormData.stream().collect(Collectors.groupingBy(com.ruoyi.activiti.domain.ActWorkflowFormData::getTaskNodeName));
        collect.forEach((key, value) -> {
            HistoryDataDTO returnHistoryFromDataDTO = new HistoryDataDTO();
            returnHistoryFromDataDTO.setTaskNodeName(value.get(0).getTaskNodeName());
            returnHistoryFromDataDTO.setCreateName(value.get(0).getCreateName());
            returnHistoryFromDataDTO.setCreatedDate(sdf.format(value.get(0).getCreateTime()));

            List<HistoryFormDataDTO> list = new LinkedList<>();
            for (ActWorkflowFormData awfd : value){
                Map<Object, Object> formData = JSON.parseObject(awfd.getFormData(), HashMap.class);
                Map<Object, Object> formDef = JSON.parseObject(awfd.getFormDesc(), HashMap.class);
                formDef.forEach((defName, defDesc) -> {
                    Object val = formData.get(defName);
                    list.add(new HistoryFormDataDTO(String.valueOf(defDesc), String.valueOf(val)));
                });
            }
            returnHistoryFromDataDTO.setFormHistoryDataDTO(list);

            returnHistoryFromDataDTOS.add(returnHistoryFromDataDTO);
        });
        List<HistoryDataDTO> collect1 = returnHistoryFromDataDTOS.stream()
                .sorted(Comparator.comparing(HistoryDataDTO::getCreatedDate))
                .collect(Collectors.toList());

        return collect1;
    }



}
