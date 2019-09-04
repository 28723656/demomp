package com.java.demomp.game.mapper;

import com.java.demomp.game.entity.GameRewardDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-04
 */
public interface GameRewardDayMapper extends BaseMapper<GameRewardDay> {

    // 获取用户的今日奖励
    @Select("select * from t_game_reward_day where user_id = #{userId} and TO_DAYS(create_time) = TO_DAYS(NOW())")
    GameRewardDay getRewardDay(@Param("userId") Integer userId);
}
