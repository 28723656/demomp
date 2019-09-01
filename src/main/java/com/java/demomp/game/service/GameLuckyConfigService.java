package com.java.demomp.game.service;

import com.java.demomp.game.VO.GameLuckyConfigVO;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-30
 */
public interface GameLuckyConfigService extends IService<GameLuckyConfig> {

    boolean addLuckyConfig(List<GameLuckyConfig> configList);

    List<GameLuckyConfigVO> getNewestLuckyConfig(Integer luckyId);

}
