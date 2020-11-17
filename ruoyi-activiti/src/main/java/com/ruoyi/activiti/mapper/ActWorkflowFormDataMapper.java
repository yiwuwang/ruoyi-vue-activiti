package com.ruoyi.activiti.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.activiti.domain.ActWorkflowFormData;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;

/**
 * 动态单Mapper接口
 * 
 * @author danny
 * @date 2020-11-02
 */
public interface ActWorkflowFormDataMapper
{
    /**
     * 查询动态单
     * 
     * @param id 动态单ID
     * @return 动态单
     */
    public ActWorkflowFormData selectActWorkflowFormDataById(Long id);
    /**
     * 查询动态单
     *
     * @param businessKey 动态单ID
     * @return 动态单
     */
    public List<ActWorkflowFormData> selectActWorkflowFormDataByBusinessKey(String businessKey);

    /**
     * 查询动态单列表
     * 
     * @param ActWorkflowFormData 动态单
     * @return 动态单集合
     */
    public List<ActWorkflowFormData> selectActWorkflowFormDataList(ActWorkflowFormData ActWorkflowFormData);

    /**
     * 新增动态单
     * 
     * @param ActWorkflowFormData 动态单
     * @return 结果
     */
    public int insertActWorkflowFormData(ActWorkflowFormData ActWorkflowFormData);


    /**
     * 新增动态单
     *
     * @param
     * @return 结果
     */
    public int insertActWorkflowFormDatas(@Param("createBy") String createBy, @Param("ActWorkflowFormData")List<ActWorkflowFormData> ActWorkflowFormData, Date date ,String createName);




    /**
     * 修改动态单
     * 
     * @param ActWorkflowFormData 动态单
     * @return 结果
     */
    public int updateActWorkflowFormData(ActWorkflowFormData ActWorkflowFormData);

    /**
     * 删除动态单
     * 
     * @param id 动态单ID
     * @return 结果
     */
    public int deleteActWorkflowFormDataById(Long id);

    /**
     * 批量删除动态单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteActWorkflowFormDataByIds(Long[] ids);
}
