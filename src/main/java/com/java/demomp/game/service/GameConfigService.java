package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-27
 */
public interface GameConfigService extends IService<GameConfig> {

    boolean saveConfig(GameConfig gameConfig);
}
