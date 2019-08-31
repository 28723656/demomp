package com.java.demomp.game.mapper;

import com.java.demomp.game.entity.GameLuckyRound;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-30
 */
public interface GameLuckyRoundMapper extends BaseMapper<GameLuckyRound> {

    @Select("select * from t_game_lucky_round order by id desc limit 1")
    GameLuckyRound selectMaxId();
}
