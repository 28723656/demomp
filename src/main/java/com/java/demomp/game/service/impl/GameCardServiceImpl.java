package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameCard;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.java.demomp.game.mapper.GameCardMapper;
import com.java.demomp.game.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    GameLuckyConfigService gameLuckyConfigService;

    /**
     * 卡片删除
     * @param cardId
     * @return
     */
    public int deleteCardByCardId(Integer cardId) {

        // 如果有人在抽奖概率中引用了这张卡，就不能被删除
        List<GameLuckyConfig> cardForLuckyList = gameLuckyConfigService.getBaseMapper().selectList(new QueryWrapper<GameLuckyConfig>().eq("card_id", cardId));
        if(cardForLuckyList == null){
            // 1.删除 t_game_card
            int deleteNums = baseMapper.deleteById(cardId);
            // 2.删除t_game_config
            gameConfigService.deleteCardByCardId(cardId);
            // 3.删除t_game_cost
            gameCostService.deleteCardByCardId(cardId);
            // 4.删除t_game_myCard(未完成)
            // 5.删除t_game_upgrade
            gameUpgradeService.deleteCardByCardId(cardId);
            if(deleteNums > 0){
                return StatusCode.OK;
            }else {
                return StatusCode.ERROR;
            }
        }else {
            return StatusCode.CANNOTDELETE;
        }
    }
}
