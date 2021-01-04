package com.ruoyi.activiti.domain.dto;


import java.util.Map;

/**
 * @author KisChang
 * @date 2020-12-30
 */

public class ActFormDataSaveDTO implements java.io.Serializable {

    private Map<String, Object> data;
    private Map<String, Object> desc;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getDesc() {
        return desc;
    }

    public void setDesc(Map<String, Object> desc) {
        this.desc = desc;
    }
}
