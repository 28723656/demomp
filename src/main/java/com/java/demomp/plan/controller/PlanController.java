package com.java.demomp.plan.controller;


import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.service.PlanService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if(integer == 1){
            return new Result(true, StatusCode.OK,"添加成功");
        }else {
            return new Result(false, StatusCode.ERROR,"添加失败");
        }

    }

    @GetMapping("/{type}")
    public Result getPlanByType(@PathVariable Integer type){
      return new Result(true,StatusCode.OK,"查询成功",planService.getPlanByType(type));
    }

}
