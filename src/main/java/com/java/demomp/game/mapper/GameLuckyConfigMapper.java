package com.java.demomp.game.mapper;

import com.java.demomp.game.VO.GameLuckyConfigVO;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-30
 */
public interface GameLuckyConfigMapper extends BaseMapper<GameLuckyConfig> {


    /**
     * 暂时无用，我之前傻逼了
     * @param id
     * @param luckyId
     * @return
     */
    @Select("SELECT config.*, card. name AS name,card.top_star AS top_star " +
            "FROM t_game_lucky_config config LEFT JOIN t_game_card card on config.card_id = card.id AND card.deleted = 0 " +
            "WHERE config.round_id = #{id} AND config.lucky_id = #{luckyId} AND config.deleted = 0")
    List<GameLuckyConfigVO> getNewestLuckyConfig(@Param("id") Integer id, @Param("luckyId") Integer luckyId);
}
