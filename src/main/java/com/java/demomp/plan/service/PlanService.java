package com.java.demomp.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.plan.entity.Plan;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-17
 */
public interface PlanService extends IService<Plan> {

    Integer addPlan(Plan plan);

    List<Plan> getPlanByType(Integer type);

    Map<String, List<Plan>> getGroupPlan();

    Integer updatePlanFinishedById(Plan id);

    Integer updatePlan(Plan plan);

    Integer deletePlanById(Integer id);

     List<Object> getTreeList(Integer parentId);

    List<Object> getTreeList();
}
