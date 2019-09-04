package com.java.demomp.game.mapper;

import com.java.demomp.game.entity.GameMyCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.demomp.game.entity.GameRewardDay;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-31
 */
public interface GameMyCardMapper extends BaseMapper<GameMyCard> {

    @Select("select * from user_reward_day")
    List<GameRewardDay> getEveryDayReward();
}
