package com.java.demomp.plan.controller;


import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.service.PlanService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
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
     * @return
     */
    @PostMapping()
    public Result addPlan(@RequestBody Plan plan){
        plan.setPercent(0.00);
        Integer integer = planService.addPlan(plan);
        System.out.println("---------------------"+new Date()+"----------------");
        if(integer == 1){
            return new Result(true, StatusCode.OK,"添加成功");
        }else {
            return new Result(false, StatusCode.ERROR,"添加失败");
        }

    }

    /**
     * 通过类型返回计划
     * @param type
     * @return
     */
    @GetMapping("/{type}")
    public Result getPlanByType(@PathVariable Integer type){
      return new Result(true,StatusCode.OK,"查询成功",planService.getPlanByType(type));
    }


    /**
     * 获取所有的计划
     * @return
     */
    @GetMapping
    public Result getPlan(){
        Map<String,List<Plan>> map = new HashMap<>();
        map.put("todayPlan",planService.getPlanByType(1));
        map.put("weekPlan",planService.getPlanByType(2));
        map.put("monthPlan",planService.getPlanByType(3));
        map.put("yearPlan",planService.getPlanByType(4));
        return new Result(true,StatusCode.OK,"查询成功",map);
    }


}
