package com.java.demomp.game.service.impl;

import com.java.demomp.game.entity.GameCard;
import com.java.demomp.game.mapper.GameCardMapper;
import com.java.demomp.game.service.GameCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.game.service.GameConfigService;
import com.java.demomp.game.service.GameCostService;
import com.java.demomp.game.service.GameUpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GameCardServiceImpl extends ServiceImpl<GameCardMapper, GameCard> implements GameCardService {

    @Autowired
    GameConfigService gameConfigService;

    @Autowired
    GameCostService gameCostService;

    @Autowired
    GameUpgradeService gameUpgradeService;

    /**
     * 卡片删除
     * @param cardId
     * @return
     */
    public boolean deleteCardByCardId(Integer cardId) {
        // 1.删除 t_game_card
        int deleteNums = baseMapper.deleteById(cardId);
        // 2.删除t_game_config
        gameConfigService.deleteCardByCardId(cardId);
        // 3.删除t_game_cost
        gameCostService.deleteCardByCardId(cardId);
        // 4.删除t_game_myCard(未完成)

        // 5.删除t_game_upgrade
        gameUpgradeService.deleteCardByCardId(cardId);

        return deleteNums > 0;
    }
}
