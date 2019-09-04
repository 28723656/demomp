package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameCost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
public interface GameCostService extends IService<GameCost> {

    int deleteCardByCardId(Integer cardId);

    /**
     * 获得满级卡片的属性
     * @return
     */
    List<GameCost> getMaxList();
}
