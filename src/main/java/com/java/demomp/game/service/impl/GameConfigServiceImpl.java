package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.game.entity.GameConfig;
import com.java.demomp.game.mapper.GameConfigMapper;
import com.java.demomp.game.service.GameConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-27
 */
@Service
public class GameConfigServiceImpl extends ServiceImpl<GameConfigMapper, GameConfig> implements GameConfigService {

    /**
     * 保存配置信息
     * @param gameConfig
     * @return
     */
    public boolean saveConfig(GameConfig gameConfig) {
       // 先删除以前的配置信息
        int deleteNums = baseMapper.delete(new UpdateWrapper<GameConfig>().eq("card_id", gameConfig.getCardId()));
        // 插入新的游戏配置
        int insertNums = baseMapper.insert(gameConfig);
        return insertNums > 0;
    }
}
