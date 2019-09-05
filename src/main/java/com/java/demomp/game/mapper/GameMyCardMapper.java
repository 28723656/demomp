package com.java.demomp.game.mapper;

import com.java.demomp.game.entity.GameCost;
import com.java.demomp.game.entity.GameMyCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.demomp.game.entity.GameRewardDay;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 通过用户id获取用户的技能分类总和
     * @param userId
     * @return
     */
    @Select("SELECT  " +
            "skill.skill,  " +
            "SUM(IFNULL(skill.inc_coin, 0)) AS incCoin,  " +
            "SUM(IFNULL(skill.inc_experience, 0)) AS incExperience,  " +
            "SUM(IFNULL(skill.low_percent, 0)) AS lowPercent,  " +
            "SUM(IFNULL(skill.top_percent, 0)) AS topPercent  " +
            "FROM  " +
            "  user_unlock_skill skill  " +
            "WHERE  " +
            "  skill.user_id = #{userId}  " +
            "GROUP BY  " +
            "  skill.skill")
    List<GameCost> getSkillTotal(@Param("userId") Integer userId);
}
