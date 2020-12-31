package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysCustomForm;

/**
 * 自定义表单Service接口
 * 
 * @author KisChang
 * @date 2020-12-29
 */
public interface ISysCustomFormService 
{
    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    public SysCustomForm selectSysCustomFormById(Long id);

    /**
     * 查询自定义表单
     *
     * @param formKey 自定义表单Key
     * @return 自定义表单
     */
    SysCustomForm selectSysCustomFormByKey(String formKey);

    /**
     * 查询自定义表单列表
     * 
     * @param sysCustomForm 自定义表单
     * @return 自定义表单集合
     */
    public List<SysCustomForm> selectSysCustomFormList(SysCustomForm sysCustomForm);

    /**
     * 新增自定义表单
     * 
     * @param sysCustomForm 自定义表单
     * @return 结果
     */
    public int insertSysCustomForm(SysCustomForm sysCustomForm);

    /**
     * 修改自定义表单
     * 
     * @param sysCustomForm 自定义表单
     * @return 结果
     */
    public int updateSysCustomForm(SysCustomForm sysCustomForm);

    /**
     * 批量删除自定义表单
     * 
     * @param ids 需要删除的自定义表单ID
     * @return 结果
     */
    public int deleteSysCustomFormByIds(Long[] ids);

    /**
     * 删除自定义表单信息
     * 
     * @param id 自定义表单ID
     * @return 结果
     */
    public int deleteSysCustomFormById(Long id);

}
