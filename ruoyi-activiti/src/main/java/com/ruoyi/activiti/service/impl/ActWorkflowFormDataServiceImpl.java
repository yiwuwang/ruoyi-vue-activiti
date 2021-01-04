package com.ruoyi.activiti.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activiti.mapper.ActWorkflowFormDataMapper;
import com.ruoyi.activiti.domain.ActWorkflowFormData;
import com.ruoyi.activiti.service.IActWorkflowFormDataService;

/**
 * 动态单Service业务层处理
 * 
 * @author danny
 * @date 2020-12-30
 */
@Service
public class ActWorkflowFormDataServiceImpl implements IActWorkflowFormDataService 
{
    @Autowired
    private ActWorkflowFormDataMapper actWorkflowFormDataMapper;

    /**
     * 查询动态单
     * 
     * @param id 动态单ID
     * @return 动态单
     */
    @Override
    public ActWorkflowFormData selectActWorkflowFormDataById(Long id)
    {
        return actWorkflowFormDataMapper.selectActWorkflowFormDataById(id);
    }

    /**
     * 查询动态单列表
     * 
     * @param actWorkflowFormData 动态单
     * @return 动态单
     */
    @Override
    public List<ActWorkflowFormData> selectActWorkflowFormDataList(ActWorkflowFormData actWorkflowFormData)
    {
        return actWorkflowFormDataMapper.selectActWorkflowFormDataList(actWorkflowFormData);
    }

    @Override
    public List<ActWorkflowFormData> selectActWorkflowFormDataByBusinessKey(String businessKey) {
        ActWorkflowFormData param = new ActWorkflowFormData();
        param.setBusinessKey(businessKey);
        return actWorkflowFormDataMapper.selectActWorkflowFormDataList(param);
    }

    /**
     * 新增动态单
     * 
     * @param actWorkflowFormData 动态单
     * @return 结果
     */
    @Override
    public int insertActWorkflowFormData(ActWorkflowFormData actWorkflowFormData)
    {
        actWorkflowFormData.setCreateTime(DateUtils.getNowDate());
        return actWorkflowFormDataMapper.insertActWorkflowFormData(actWorkflowFormData);
    }

    /**
     * 修改动态单
     * 
     * @param actWorkflowFormData 动态单
     * @return 结果
     */
    @Override
    public int updateActWorkflowFormData(ActWorkflowFormData actWorkflowFormData)
    {
        return actWorkflowFormDataMapper.updateActWorkflowFormData(actWorkflowFormData);
    }

    /**
     * 批量删除动态单
     * 
     * @param ids 需要删除的动态单ID
     * @return 结果
     */
    @Override
    public int deleteActWorkflowFormDataByIds(Long[] ids)
    {
        return actWorkflowFormDataMapper.deleteActWorkflowFormDataByIds(ids);
    }

    /**
     * 删除动态单信息
     * 
     * @param id 动态单ID
     * @return 结果
     */
    @Override
    public int deleteActWorkflowFormDataById(Long id)
    {
        return actWorkflowFormDataMapper.deleteActWorkflowFormDataById(id);
    }
}
