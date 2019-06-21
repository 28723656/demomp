package com.java.demomp.plan.controller;


import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.service.PlanService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/plan/plan")
public class PlanController {

    @Autowired
    PlanService planService;

    /**
     * 添加计划
     *
     * @return
     */
    @PostMapping()
    public Result addPlan(@RequestBody Plan plan) {
        plan.setPercent(0.00);
        Integer integer = planService.addPlan(plan);
        if (integer == 1) {
            return new Result(true, StatusCode.OK, "添加成功");
        } else {
            return new Result(false, StatusCode.ERROR, "添加失败");
        }

    }

    /**
     * 通过类型返回计划
     *
     * @param type
     * @return
     */
    @GetMapping("/{type}")
    public Result getPlanByType(@PathVariable Integer type) {

        List<Plan> planList = planService.getPlanByType(type);
        if (planList != null && planList.size() > 0) {
            return new Result(true, StatusCode.OK, "查询成功", planList);
        } else {
            return new Result(false, StatusCode.NODATA, "没有数据");
        }


    }


    /**
     * 获取所有的计划
     *
     * @return
     */
    @GetMapping
    public Result getPlan() {
        return new Result(true, StatusCode.OK, "查询成功", planService.getGroupPlan());
    }


    /**
     * 改变日计划的完成状态
     *
     * @param plan
     * @return
     */
    @PutMapping
    public Result updatePlanFinishedById(@RequestBody Plan plan) {
        Integer updateNum = planService.updatePlanFinishedById(plan);
        if (updateNum > 0) {
            return new Result(true, StatusCode.OK, "修改成功");
        } else {
            return new Result(false, StatusCode.ERROR, "修改失败");
        }

    }

    /**
     * 修改计划
     */

    @PutMapping("/update")
    public Result updatePlan(@RequestBody Plan plan) {
        Integer updateNum = planService.updatePlan(plan);
        if (updateNum > 0) {
            return new Result(true, StatusCode.OK, "修改成功", planService.updatePlan(plan));
        } else {
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }


    /**
     * 删除计划   非最子节点删除的问题未解决
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Integer id) {
        Integer deleteNum = planService.deletePlanById(id);
        if (deleteNum > 0) {
            return new Result(true, StatusCode.OK, "删除成功");
        } else {
            return new Result(false, StatusCode.ERROR, "删除失败，或是有子节点");
        }
    }

    /**
     * 通过父节点获取树列表   默认最大的父节点为-1
     *
     * @param parentId
     * @return
     */
    @GetMapping("/tree/{parentId}")
    public Result getTreeList(@PathVariable Integer parentId) {
        parentId = parentId == -1 ? null : parentId;
        List<Object> treeList = planService.getTreeList(parentId);
        if(treeList != null && treeList.size() > 0){
            return new Result(true, StatusCode.OK, "查询树成功", planService.getTreeList(parentId));
        }else{
            return new Result(false, StatusCode.ERROR, "没有数据或查询错误");
        }

    }


    /**
     * 获得树的List
     */
    @GetMapping("/tree")
    public Result getTreeList() {
        List<Object> treeList = planService.getTreeList();
        if (treeList != null && treeList.size() > 0) {
            return new Result(true, StatusCode.OK, "查询树成功", treeList);
        } else {
            return new Result(false, StatusCode.ERROR, "没有树");
        }

    }


}
