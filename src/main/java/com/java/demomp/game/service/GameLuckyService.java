package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameLucky;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-29
 */
public interface GameLuckyService extends IService<GameLucky> {

    boolean deleteLucky(Integer id);

    Map<String,Object> openLucky(Integer userId, Integer luckyId, Integer openTimes);
}
