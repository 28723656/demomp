package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameLuckyRound;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-30
 */
public interface GameLuckyRoundService extends IService<GameLuckyRound> {

    GameLuckyRound selectMaxId();
}
