package com.java.demomp.plan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.plan.entity.PlanHabit;
import com.java.demomp.plan.service.PlanHabitService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-18
 */
@RestController
@RequestMapping("/plan/habit")
public class PlanHabitController {

    @Autowired
    PlanHabitService planHabitService;

    /**
     * 通过id查询
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public Result getByUserId(@PathVariable Integer userId){
        List<PlanHabit> planHabitList = planHabitService.getBaseMapper().selectList(new QueryWrapper<PlanHabit>().eq("user_id", userId).orderByDesc("create_time"));
        return new Result(true, StatusCode.OK,"查询成功",planHabitList);
    }



    /**
     * 添加
     * @param planHabit
     * @return
     */
    @PostMapping("/user")
    public Result insertHabit(@RequestBody PlanHabit planHabit){
        planHabit.setStartTime(LocalDateTime.now());
        planHabit.setEndTime(LocalDateTime.now().plusDays(planHabit.getAllDays()));
        planHabitService.save(planHabit);
        return new Result(true, StatusCode.OK,"添加成功");

    }


    /**
     * 修改
     * @param planHabit
     * @return
     */
    @PutMapping("/user")
    public Result updateHabit(@RequestBody PlanHabit planHabit){
        planHabitService.updateById(planHabit);
        return new Result(true, StatusCode.OK,"修改");

    }

    /**
     * 完成今日的习惯
     * @return
     */
    @PutMapping("/{id}/{todayFinish}")
    public Result finishHabit(@PathVariable Integer id,@PathVariable Integer todayFinish){
        PlanHabit planHabit = new PlanHabit();
        planHabit.setId(id);
        planHabit.setTodayFinish(todayFinish);
        planHabit.setFinishTime(LocalDateTime.now());
        planHabitService.updateById(planHabit);
        return new Result(true, StatusCode.OK,"修改");
    }
}
