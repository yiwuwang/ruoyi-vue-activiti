package com.ruoyi.activiti.service;

import java.util.List;
import com.ruoyi.activiti.domain.ActWorkflowFormData;

/**
 * 动态单Service接口
 * 
 * @author danny
 * @date 2020-12-30
 */
public interface IActWorkflowFormDataService 
{
    /**
     * 查询动态单
     * 
     * @param id 动态单ID
     * @return 动态单
     */
    public ActWorkflowFormData selectActWorkflowFormDataById(Long id);

    /**
     * 查询动态单列表
     * 
     * @param actWorkflowFormData 动态单
     * @return 动态单集合
     */
    public List<ActWorkflowFormData> selectActWorkflowFormDataList(ActWorkflowFormData actWorkflowFormData);

    public List<ActWorkflowFormData> selectActWorkflowFormDataByBusinessKey(String businessKey);

    /**
     * 新增动态单
     * 
     * @param actWorkflowFormData 动态单
     * @return 结果
     */
    public int insertActWorkflowFormData(ActWorkflowFormData actWorkflowFormData);

    /**
     * 修改动态单
     * 
     * @param actWorkflowFormData 动态单
     * @return 结果
     */
    public int updateActWorkflowFormData(ActWorkflowFormData actWorkflowFormData);

    /**
     * 批量删除动态单
     * 
     * @param ids 需要删除的动态单ID
     * @return 结果
     */
    public int deleteActWorkflowFormDataByIds(Long[] ids);

    /**
     * 删除动态单信息
     * 
     * @param id 动态单ID
     * @return 结果
     */
    public int deleteActWorkflowFormDataById(Long id);
}
