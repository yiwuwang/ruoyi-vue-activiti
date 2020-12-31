package com.ruoyi.system.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysCustomForm;
import com.ruoyi.system.service.ISysCustomFormService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自定义表单Controller
 * 
 * @author KisChang
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/form")
public class SysCustomFormController extends BaseController
{
    @Autowired
    private ISysCustomFormService sysCustomFormService;

    /**
     * 查询自定义表单列表
     */
    @PreAuthorize("@ss.hasPermi('system:form:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomForm sysCustomForm)
    {
        startPage();
        List<SysCustomForm> list = sysCustomFormService.selectSysCustomFormList(sysCustomForm);
        return getDataTable(list);
    }

    /**
     * 导出自定义表单列表
     */
    @PreAuthorize("@ss.hasPermi('system:form:export')")
    @Log(title = "自定义表单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomForm sysCustomForm)
    {
        List<SysCustomForm> list = sysCustomFormService.selectSysCustomFormList(sysCustomForm);
        ExcelUtil<SysCustomForm> util = new ExcelUtil<SysCustomForm>(SysCustomForm.class);
        return util.exportExcel(list, "form");
    }

    /**
     * 获取自定义表单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:form:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCustomFormService.selectSysCustomFormById(id));
    }

    /**
     * 新增自定义表单
     */
    @PreAuthorize("@ss.hasPermi('system:form:add')")
    @Log(title = "自定义表单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCustomForm sysCustomForm)
    {
        return toAjax(sysCustomFormService.insertSysCustomForm(sysCustomForm));
    }

    /**
     * 修改自定义表单
     */
    @PreAuthorize("@ss.hasPermi('system:form:edit')")
    @Log(title = "自定义表单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCustomForm sysCustomForm)
    {
        return toAjax(sysCustomFormService.updateSysCustomForm(sysCustomForm));
    }

    /**
     * 删除自定义表单
     */
    @PreAuthorize("@ss.hasPermi('system:form:remove')")
    @Log(title = "自定义表单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCustomFormService.deleteSysCustomFormByIds(ids));
    }
}
