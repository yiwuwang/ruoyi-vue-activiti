package com.ruoyi.activiti.modeler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.ruoyi.activiti.domain.ModelerVo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.List;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

@Controller
public class ModelerController extends BaseController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ModelEditorJsonRestResource.class);

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/modeler/list")
    @ResponseBody
    public TableDataInfo list(ModelEntityImpl modelEntity) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();

        // 条件过滤
        if (com.ruoyi.common.utils.StringUtils.isNotBlank(modelEntity.getKey())) {
            modelQuery.modelKey(modelEntity.getKey());
        }
        if (com.ruoyi.common.utils.StringUtils.isNotBlank(modelEntity.getName())) {
            modelQuery.modelNameLike("%" + modelEntity.getName() + "%");
        }

        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        List<Model> resultList = modelQuery.listPage((pageNum - 1) * pageSize, pageSize);

        Page<Model> list = new Page<>();
        list.addAll(resultList);

        list.setTotal(modelQuery.count());
        list.setPageNum(pageNum);
        list.setPageSize(pageSize);

        return getDataTable(list);
    }
    @GetMapping("/modeler/newModel")
    @ResponseBody
    public AjaxResult newModel() throws UnsupportedEncodingException
    {
        // 初始化一个空模型
        Model model = repositoryService.newModel();
        // 设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        String id = model.getId();
        // 完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.replace("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        return new AjaxResult(HttpStatus.SUCCESS, "创建模型成功", model.getId());
    }



    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "/modeler/deploy/{modelId}")
    @ResponseBody
    public AjaxResult deploy(@PathVariable("modelId") String modelId, RedirectAttributes redirectAttributes) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            LOGGER.info("部署成功，部署ID=" + deployment.getId());
            return AjaxResult.success("部署成功");
        } catch (Exception e) {
            LOGGER.error("根据模型部署流程失败：modelId={}", modelId, e);

        }
        return AjaxResult.error("部署失败");
    }

    /**
     * 导出model的xml文件
     */
    @RequestMapping(value = "/modeler/export/{modelId}")
    @ResponseBody
    public AjaxResult export(@PathVariable("modelId") String modelId) {
        OutputStream out = null;
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 流程非空判断
            if (!CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

                ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
                String filename = bpmnModel.getMainProcess().getId() + ".bpmn";

                File file  = new File(getAbsoluteFile(filename));
                if(file.exists()){
                    file.delete();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bpmnBytes,0,bpmnBytes.length);
                fos.flush();
                fos.close();
                return AjaxResult.success(filename);
            } else {
                return AjaxResult.error();
            }

        } catch (Exception e) {
            LOGGER.error("导出model的xml文件失败：modelId={}", modelId, e);
            return AjaxResult.error("导出model的xml文件失败：modelId={}", modelId);

        }
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }


    @Log(title = "流程模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/modeler/remove/{ids}")
    @ResponseBody
    public AjaxResult remove(@PathVariable String ids) {
        try {
            repositoryService.deleteModel(ids);
            return AjaxResult.success();
        }
        catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}
