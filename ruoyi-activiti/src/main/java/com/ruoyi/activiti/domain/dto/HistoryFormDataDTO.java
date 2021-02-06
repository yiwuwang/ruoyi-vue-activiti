package com.ruoyi.activiti.domain.dto;

/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/11/3-14:02
 * 版本   开发者     日期
 * 1.0    Danny    2020/11/3
 */
public class HistoryFormDataDTO {
    private String title;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HistoryFormDataDTO() {
    }

    public HistoryFormDataDTO(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
