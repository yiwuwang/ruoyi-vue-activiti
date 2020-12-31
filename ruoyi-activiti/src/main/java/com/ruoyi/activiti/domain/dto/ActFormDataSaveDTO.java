package com.ruoyi.activiti.domain.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author KisChang
 * @date 2020-12-30
 */
@Data
public class ActFormDataSaveDTO implements java.io.Serializable {

    private Map<String, Object> data;
    private Map<String, Object> desc;

}
