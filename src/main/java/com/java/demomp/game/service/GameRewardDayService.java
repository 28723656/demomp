package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameRewardDay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-04
 */
public interface GameRewardDayService extends IService<GameRewardDay> {

    GameRewardDay getRewardDay(Integer userId);
}
