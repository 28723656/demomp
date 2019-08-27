package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.game.entity.GameCost;
import com.java.demomp.game.mapper.GameCostMapper;
import com.java.demomp.game.service.GameCostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
@Service
public class GameCostServiceImpl extends ServiceImpl<GameCostMapper, GameCost> implements GameCostService {

    /**
     * 通过cardId删除
     * @param cardId
     * @return
     */
    public int deleteCardByCardId(Integer cardId) {
        return baseMapper.delete(new UpdateWrapper<GameCost>().eq("card_id",cardId));
    }
}
