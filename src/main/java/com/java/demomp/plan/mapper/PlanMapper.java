package com.java.demomp.plan.mapper;

import com.java.demomp.plan.entity.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-17
 */
public interface PlanMapper extends BaseMapper<Plan> {

    // 通过父id统计百分比总数
    @Select("select sum(percent) from t_plan where parent_id =#{weekPlanParentId} and deleted=0 ")
    Double selectSumPercent(Integer weekPlanParentId);

    @Select("")
    Map<String, Object> getTreeDataByParentId(Object o);
}
