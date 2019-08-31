package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameCard;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.java.demomp.game.entity.GamePercent;
import com.java.demomp.game.mapper.GameCardMapper;
import com.java.demomp.game.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    GamePercentService gamePercentService;

    /**
     * 卡片删除
     * @param cardId
     * @return
     */
    public int deleteCardByCardId(Integer cardId) {

        // 如果有人在抽奖概率中引用了这张卡，就不能被删除
        List<GameLuckyConfig> cardForLuckyList = gameLuckyConfigService.getBaseMapper().selectList(new QueryWrapper<GameLuckyConfig>().eq("card_id", cardId));
        if(cardForLuckyList.size() == 0){
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

    /**
     * 保存卡片
     * @param gameCard
     */
    public void saveCard(GameCard gameCard) {
        baseMapper.insert(gameCard);
        // 2.像t_game_percent表中插入默认数据
        List<GamePercent> list = new ArrayList<>();
        list.add(generateEntity(1,70,gameCard.getId()));
        list.add(generateEntity(2,20,gameCard.getId()));
        list.add(generateEntity(5,9,gameCard.getId()));
        list.add(generateEntity(20,1,gameCard.getId()));
        gamePercentService.saveBatch(list);
    }


    public GamePercent generateEntity(int nums,long percent,int cardId ){
        GamePercent gamePercent = new GamePercent();
        gamePercent.setNums(nums);
        gamePercent.setPercent(BigDecimal.valueOf(percent));
        gamePercent.setCardId(cardId);
        return gamePercent;
    }
}
