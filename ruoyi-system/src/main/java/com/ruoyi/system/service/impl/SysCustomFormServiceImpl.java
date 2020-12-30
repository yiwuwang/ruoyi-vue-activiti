package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysCustomFormMapper;
import com.ruoyi.system.domain.SysCustomForm;
import com.ruoyi.system.service.ISysCustomFormService;

/**
 * 自定义表单Service业务层处理
 * 
 * @author KisChang
 * @date 2020-12-29
 */
@Service
public class SysCustomFormServiceImpl implements ISysCustomFormService 
{
    @Autowired
    private SysCustomFormMapper sysCustomFormMapper;

    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    @Override
    public SysCustomForm selectSysCustomFormById(Long id)
    {
        return sysCustomFormMapper.selectSysCustomFormById(id);
    }

    @Override
    public SysCustomForm selectSysCustomFormByKey(String formKey) {
        return sysCustomFormMapper.selectSysCustomFormByKey(formKey);
    }

    /**
     * 查询自定义表单列表
     * 
     * @param sysCustomForm 自定义表单
     * @return 自定义表单
     */
    @Override
    public List<SysCustomForm> selectSysCustomFormList(SysCustomForm sysCustomForm)
    {
        return sysCustomFormMapper.selectSysCustomFormList(sysCustomForm);
    }

    /**
     * 新增自定义表单
     * 
     * @param sysCustomForm 自定义表单
     * @return 结果
     */
    @Override
    public int insertSysCustomForm(SysCustomForm sysCustomForm)
    {
        return sysCustomFormMapper.insertSysCustomForm(sysCustomForm);
    }

    /**
     * 修改自定义表单
     * 
     * @param sysCustomForm 自定义表单
     * @return 结果
     */
    @Override
    public int updateSysCustomForm(SysCustomForm sysCustomForm)
    {
        return sysCustomFormMapper.updateSysCustomForm(sysCustomForm);
    }

    /**
     * 批量删除自定义表单
     * 
     * @param ids 需要删除的自定义表单ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomFormByIds(Long[] ids)
    {
        return sysCustomFormMapper.deleteSysCustomFormByIds(ids);
    }

    /**
     * 删除自定义表单信息
     * 
     * @param id 自定义表单ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomFormById(Long id)
    {
        return sysCustomFormMapper.deleteSysCustomFormById(id);
    }
}
