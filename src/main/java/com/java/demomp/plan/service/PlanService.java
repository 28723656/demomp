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

    Integer addPlan(Plan plan,Integer userId);

    List<Plan> getPlanByType(Integer type,Integer userId);

    Map<String, List<Plan>> getGroupPlan(Integer userId);

    Integer updatePlanFinishedById(Plan id,Integer userId);

    Integer updatePlan(Plan plan,Integer userId);

    Integer deletePlanById(Integer id,Integer userId);

     List<Object> getTreeList(Integer parentId,Integer userId);

    List<Object> getTreeList(Integer userId);

    List<Plan> getBasePlanByUserId(Integer userId);

    Boolean insertThreeBasePlan(Integer userId);
}
