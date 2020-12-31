package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.dto.HistoryDataDTO;

import java.util.List;

/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/11/3-10:19
 * 版本   开发者     日期
 * 1.0    Danny    2020/11/3
 */
public interface IFormHistoryDataService {

    public List<HistoryDataDTO> historyDataShow(String businessKey);
}
