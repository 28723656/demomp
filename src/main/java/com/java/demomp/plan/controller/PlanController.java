package com.java.demomp.plan.controller;


import com.java.demomp.admin.entity.User;
import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.service.PlanService;
import com.java.demomp.util.IpUtil;
import com.java.demomp.util.IpUtil2;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @PostMapping("/{userId}")
    public Result addPlan(@RequestBody Plan plan,@PathVariable Integer userId) {
        plan.setPercent(0.00);
        Integer integer = planService.addPlan(plan,userId);
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
    @GetMapping("/{type}/{userId}")
    public Result getPlanByType(@PathVariable Integer type,@PathVariable Integer userId) {

        List<Plan> planList = planService.getPlanByType(type,userId);
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
    @GetMapping("/{userId}")
    public Result getPlan(@PathVariable Integer userId) {
        return new Result(true, StatusCode.OK, "查询成功", planService.getGroupPlan(userId));
    }


    /**
     * 改变日计划的完成状态
     *
     * @param plan
     * @return
     */
    @PutMapping("/{userId}")
    public Result updatePlanFinishedById(@RequestBody Plan plan,@PathVariable Integer userId) {
        Integer updateNum = planService.updatePlanFinishedById(plan,userId);
        if (updateNum > 0) {
            return new Result(true, StatusCode.OK, "修改成功");
        } else {
            return new Result(false, StatusCode.ERROR, "修改失败");
        }

    }

    /**
     * 修改计划
     */

    @PutMapping("/update/{userId}")
    public Result updatePlan(@RequestBody Plan plan,@PathVariable Integer userId) {
        Integer updateNum = planService.updatePlan(plan,userId);
        if (updateNum > 0) {
            return new Result(true, StatusCode.OK, "修改成功", planService.updatePlan(plan,userId));
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
    @DeleteMapping("/{id}/{userId}")
    public Result deleteById(@PathVariable Integer id,@PathVariable Integer userId) {
        Integer deleteNum = planService.deletePlanById(id,userId);
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
    @GetMapping("/tree/{parentId}/{userId}")
    public Result getTreeList(@PathVariable Integer parentId,@PathVariable Integer userId) {
        parentId = parentId == -1 ? null : parentId;
        List<Object> treeList = planService.getTreeList(parentId,userId);
        if(treeList != null && treeList.size() > 0){
            return new Result(true, StatusCode.OK, "查询树成功", planService.getTreeList(parentId,userId));
        }else{
            return new Result(false, StatusCode.ERROR, "没有数据或查询错误");
        }

    }


    /**
     * 获得树的List
     */
    @GetMapping("/tree/{userId}")
    public Result getTreeList(@PathVariable Integer userId) {
        List<Object> treeList = planService.getTreeList(userId);
        if (treeList != null && treeList.size() > 0) {
            return new Result(true, StatusCode.OK, "查询树成功", treeList);
        } else {
            return new Result(false, StatusCode.ERROR, "没有树");
        }

    }


}
