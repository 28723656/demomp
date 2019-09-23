package com.java.demomp.plan.mapper;

import com.java.demomp.plan.entity.PlanHabit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-18
 */
public interface PlanHabitMapper extends BaseMapper<PlanHabit> {

    @Update("update t_plan_habit set finish_time =NULL ,today_finish = 0 where type in (1,2) and deleted = 0 ")
    void updateFinishTime();
}
