package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.domain.vo.ActReDeploymentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/10/23-15:47
 * 版本   开发者     日期
 * 1.0    Danny    2020/10/23
 */
public interface ActReDeploymentMapper {


        public List<ActReDeploymentVO> selectActReDeploymentByIds(@Param("ids") Set<String> ids);

}
