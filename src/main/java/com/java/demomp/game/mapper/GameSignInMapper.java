package com.java.demomp.game.mapper;

import com.java.demomp.game.entity.GameSignIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-08
 */
public interface GameSignInMapper extends BaseMapper<GameSignIn> {

    @Select("SELECT * FROM t_game_sign_in s WHERE day(NOW()) = s.day AND s.deleted = 0")
    GameSignIn findTodayReward();
}
