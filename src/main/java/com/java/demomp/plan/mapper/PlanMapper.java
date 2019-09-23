package com.java.demomp.plan.mapper;

import com.java.demomp.plan.entity.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.demomp.plan.service.PlanService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-17
 */
public interface PlanMapper extends BaseMapper<Plan> {

    // 通过父id统计百分比总数
    @Select("select sum(percent) from t_plan where parent_id =#{weekPlanParentId} and user_id=#{userId} and deleted=0 ")
    Double selectSumPercent(@Param("weekPlanParentId") Integer weekPlanParentId,@Param("userId") Integer userId);

    @Select("")
    Map<String, Object> getTreeDataByParentId(Object o);

    /**
     * 今日计划
     * @return
     */
    @Select("select * from t_plan where to_days(start_time) = to_days(now()) and type =1 and  deleted = 0 and user_id=#{userId}")
    List<Plan> getTodayPlanCommon(@Param("userId") Integer userId);

    /**
     * 本周计划
     * @return
     */
    @Select("select * from t_plan where (YEARWEEK(start_time,1) = YEARWEEK(NOW(),1) and base = 0 or base =1) and user_id = #{userId} and type = 2  and deleted = 0")
    List<Plan> getWeekPlanCommon(@Param("userId") Integer userId);

    /**
     * 本月计划
     * @return
     */
    @Select("SELECT * FROM t_plan WHERE (DATE_FORMAT( start_time, '%Y%m' ) = DATE_FORMAT( NOW() , '%Y%m' ) and  base = 0  or  base = 1)   and user_id = #{userId} and deleted = 0 and  type = 3")
    List<Plan> getMonthPlanCommon(@Param("userId") Integer userId);

    /**
     * 本年计划
     * @param userId
     * @return
     */
    @Select("SELECT * FROM t_plan WHERE (YEAR( start_time) = YEAR( NOW() ) and  base = 0  or  base = 1)   and user_id = #{userId} and deleted = 0 and  type = 4")
    List<Plan> getYearPlanCommon(Integer userId);

    /**
     * 未完成的日计划
     * @param userId
     * @return
     */
    @Select("SELECT * FROM t_plan WHERE( finished = 0 OR to_days(actual_time) = to_days(now())  )and to_days(create_time) != to_days(now()) AND type = 1 AND deleted = 0 AND user_id = #{userId} order by start_time desc" )
    List<Plan> getTodayUnFinishedPlanCommon(Integer userId);

    /**
     * 更新某个东西
     * @param finished
     * @param percent
     * @param finishTime
     * @param id
     */
    @Update("update t_plan set finished=#{finished} ,percent=#{percent} ,actual_time=#{finishTime}  where id=#{id} and deleted = 0")
    void updateFinished(@Param("finished") Integer finished,@Param("percent") Double percent,@Param("finishTime") LocalDateTime finishTime,@Param("id") Integer id);
}
