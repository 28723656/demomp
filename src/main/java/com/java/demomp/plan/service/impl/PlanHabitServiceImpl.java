package com.java.demomp.plan.service.impl;

import com.java.demomp.plan.entity.PlanHabit;
import com.java.demomp.plan.mapper.PlanHabitMapper;
import com.java.demomp.plan.service.PlanHabitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-18
 */
@Service
public class PlanHabitServiceImpl extends ServiceImpl<PlanHabitMapper, PlanHabit> implements PlanHabitService {

    public void updateFinishTime() {
        baseMapper.updateFinishTime();
    }
}
