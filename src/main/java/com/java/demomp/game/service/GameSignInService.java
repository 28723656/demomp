package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameSignIn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-08
 */
public interface GameSignInService extends IService<GameSignIn> {

    GameSignIn findTodayReward();
}
