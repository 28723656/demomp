package com.java.demomp.plan.service;

import com.java.demomp.plan.entity.PlanHabit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-18
 */
public interface PlanHabitService extends IService<PlanHabit> {

    void updateFinishTime();
}
