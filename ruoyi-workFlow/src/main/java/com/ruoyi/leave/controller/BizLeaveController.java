package com.ruoyi.leave.controller;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.leave.domain.BizLeave;
import com.ruoyi.leave.service.IBizLeaveService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 请假流程Controller
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/workflow/leave")
public class BizLeaveController extends BaseController {
    @Autowired
    private IBizLeaveService bizLeaveService;


    /**
     * 查询请假流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave')")
    @GetMapping("/list")
    public TableDataInfo list(BizLeave bizLeave) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            bizLeave.setCreateBy(SecurityUtils.getUsername());
        }
        bizLeave.setType("leave");
        startPage();
        List<BizLeave> list = bizLeaveService.selectBizLeaveList(bizLeave);
        return getDataTable(list);
    }



    /**
     * 导出请假流程列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave')")
    @Log(title = "请假流程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizLeave bizLeave) {
        bizLeave.setType("leave");
        List<BizLeave> list = bizLeaveService.selectBizLeaveList(bizLeave);
        ExcelUtil<BizLeave> util = new ExcelUtil<BizLeave>(BizLeave.class);
        return util.exportExcel(list, "leave");
    }

    /**
     * 获取请假流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bizLeaveService.selectBizLeaveById(id));
    }

    /**
     * 新增请假流程
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave')")
    @Log(title = "请假流程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizLeave bizLeave) {
        Long userId = SecurityUtils.getUserId();
        if (SysUser.isAdmin(userId)) {
            return AjaxResult.error("提交申请失败：不允许管理员提交申请！");
        }
        bizLeave.setType("leave");
        return toAjax(bizLeaveService.insertBizLeave(bizLeave));
    }

    /**
     * 修改请假流程
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave')")
    @Log(title = "请假流程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizLeave bizLeave) {
        return toAjax(bizLeaveService.updateBizLeave(bizLeave));
    }

    /**
     * 删除请假流程
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave')")
    @Log(title = "请假流程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizLeaveService.deleteBizLeaveByIds(ids));
    }

    /**
     * 提交申请
     */
    @Log(title = "请假业务", businessType = BusinessType.UPDATE)
    @PostMapping( "/submitApply/{id}")
    @ResponseBody
    public AjaxResult submitApply(@PathVariable Long id) {
        BizLeave leave = bizLeaveService.selectBizLeaveById(id);
        String applyUserId = SecurityUtils.getUsername();
        bizLeaveService.submitApply(leave, applyUserId, "leave", new HashMap<>());
        return AjaxResult.success();
    }

    /**
     * 我的待办列表
     * @return
     */
    @GetMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(BizLeave bizLeave) {
        bizLeave.setType("leave");
        List<BizLeave> list = bizLeaveService.findTodoTasks(bizLeave, SecurityUtils.getUsername());
        return getDataTable(list);
    }

    /**
     * 我的已办列表
     * @param bizLeave
     * @return
     */
    @GetMapping("/taskDoneList")
    @ResponseBody
    public TableDataInfo taskDoneList(BizLeave bizLeave) {
        bizLeave.setType("leave");
        List<BizLeave> list = bizLeaveService.findDoneTasks(bizLeave, SecurityUtils.getUsername());
        return getDataTable(list);
    }


}
