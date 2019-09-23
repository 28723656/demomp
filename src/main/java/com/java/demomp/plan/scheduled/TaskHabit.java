package com.java.demomp.plan.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.plan.entity.PlanHabit;
import com.java.demomp.plan.entity.PlanHabitRecord;
import com.java.demomp.plan.service.PlanHabitRecordService;
import com.java.demomp.plan.service.PlanHabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 风往西边吹丶
 * @create 2019-09-04 21:14
 */

@Component
public class TaskHabit {


    @Autowired
    PlanHabitService planHabitService;

    @Autowired
    PlanHabitRecordService planHabitRecordService;


    /**
     * 记录用户完成的习惯
     * 0 0/5 * * * ?   每5分钟执行一次
     * <p>
     * 1 0 0 * * ?   每天凌晨1秒执行
     *
     * 5 0 0 * * ?   当前
     */
    @Scheduled(cron = "5 0 0 * * ?")
    public void recordHabit() {

        // 1.扫描 habit表，把type等于1和2的数据，记录在record表里面
        List<PlanHabit> habitList = planHabitService.list(new QueryWrapper<PlanHabit>().in("type", 1, 2));

        List<PlanHabitRecord> habitRecordList = new ArrayList<>();
        if (habitList.size() > 0) {
            for (int i = 0; i < habitList.size(); i++) {
                // 获得实体对象
                PlanHabit planHabit = habitList.get(i);

                PlanHabitRecord planHabitRecord = new PlanHabitRecord();
                planHabitRecord.setHabitId(planHabit.getId());
                planHabitRecord.setIsFinished(planHabit.getTodayFinish());
                planHabitRecord.setFinishedTime(planHabit.getFinishTime());
                habitRecordList.add(planHabitRecord);
            }
        }
        // 批量插入
        planHabitRecordService.saveBatch(habitRecordList);


        // 2.对于 今日没有完成任务的习惯，在unfinished_days上面加1，type改成2，keep_days 减1
        //    如果 unfinished_days 等于3的话，把type改成3，keep_days减1，
        //   并，清空today_finish和finish_time数据
        List<PlanHabit> planHabits = new ArrayList<>();
        for (int i = 0; i < habitList.size(); i++) {
            // 获得实体对象
            PlanHabit planHabit = habitList.get(i);
            if (planHabit.getTodayFinish() == 1) {
                // 如果是完成了的计划
                planHabit.setKeepDays(planHabit.getKeepDays() + 1);
                planHabit.setRealKeepDays(planHabit.getRealKeepDays() + 1);

                // 把没有完成的天数设置为0天
                planHabit.setUnfinishedDays(0);

                if (planHabit.getKeepDays() == planHabit.getAllDays()) {
                    // 如果习惯的天数达到了
                    if (planHabit.getType() == 1) {
                        planHabit.setType(4);
                    } else if (planHabit.getType() == 2) {
                        planHabit.setType(5);
                    }
                }
            } else if (planHabit.getTodayFinish() == 0) {
                // 如果没有完成今日的任务
                planHabit.setUnfinishedDays(planHabit.getUnfinishedDays() + 1);
                planHabit.setType(2);
                if (planHabit.getUnfinishedDays() == 3) {
                    // 如果未完成的天数达到3，把type设置为3
                    planHabit.setType(3);
                }
                if (planHabit.getKeepDays() > 0) {
                    planHabit.setKeepDays(planHabit.getKeepDays() - 1);
                }
            }
            planHabits.add(planHabit);
        }
            planHabitService.updateBatchById(planHabits);


        //  // 最后清空today_finish和finish_time数据
        planHabitService.updateFinishTime();
    }

}
