package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.*;
import com.java.demomp.game.mapper.GameMyCardMapper;
import com.java.demomp.game.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-31
 */
@Service
public class GameMyCardServiceImpl extends ServiceImpl<GameMyCardMapper, GameMyCard> implements GameMyCardService {

    @Autowired
    GameUpgradeService gameUpgradeService;

    @Autowired
    GameRecordMyCardService gameRecordMyCardService;

    @Autowired
    GameMyMoneyService gameMyMoneyService;

    @Autowired
    GameCostService gameCostService;
    /**
     * 升星或者升级
     * @param userId
     * @param cardId
     * @param updateType
     * @return
     */
    @Transactional
   synchronized public boolean updateCardStar(Integer userId, Integer cardId, Integer updateType) {

        // 找到用户当前卡片的数量
        GameMyCard myCard = baseMapper.selectOne(new QueryWrapper<GameMyCard>().eq("user_id", userId).eq("card_id", cardId).last("limit 1"));
        if(myCard == null){
            // 不可能发生的事件，但是我还是防一下
            System.out.println("用户卡片数据异常");
            return false;
        }
        // 1.如果是升星，检测用于的卡片数量够不够
        if(updateType == 2){
            // 找到升星需要的数量
            GameUpgrade gameUpgrade = gameUpgradeService.getBaseMapper().selectOne(new QueryWrapper<GameUpgrade>().eq("card_id", cardId).eq("star", myCard.getCurrentStar() + 1));
            if(gameUpgrade == null){
                // 一样，不可能事件
                System.out.println("卡片升星数据异常");
                return false;
            }
            // 1.1 够->
            if(myCard.getNum() >= gameUpgrade.getNum()){
                // 1.1.1 减去升级消耗的卡片，更新用户的卡片数据
                int costCardNum = myCard.getNum()- gameUpgrade.getNum();
                int toStar = myCard.getCurrentStar()+1;
                myCard.setNum(costCardNum);
                myCard.setCurrentStar(toStar);
                baseMapper.updateById(myCard);
                // 1.1.2 记录用户本次更新的消耗情况
                GameRecordMyCard gameRecordMyCard = new GameRecordMyCard();
                gameRecordMyCard.setUserId(userId);
                gameRecordMyCard.setCardId(cardId);
                gameRecordMyCard.setCostMoney(0);
                gameRecordMyCard.setCostCardNum(costCardNum);
                gameRecordMyCard.setToStar(toStar);
                gameRecordMyCard.setToRank(myCard.getCurrentRank());
                gameRecordMyCard.setUpdateType(2);
                gameRecordMyCard.setCostType(0);
                return  gameRecordMyCardService.save(gameRecordMyCard);
            }else {
                //  1.2 不够->
                // 1.2.1 讽刺用户一句
                return false;
            }
        } else if(updateType == 1){
            //2.如果是升级，检测用户的金币或者其他货币的数量够不够
                //算了，这里先默认用金币，以后如果升级要用到其他货币再更改
            GameMyMoney gameMyMoney = gameMyMoneyService.getBaseMapper().selectOne(new QueryWrapper<GameMyMoney>().eq("user_id", userId).eq("type", 1));
            if(gameMyMoney == null){
                System.out.println("用户货币异常");
                return false;
            }
            int currentRank = myCard.getCurrentRank();
            GameCost gameCost = gameCostService.getBaseMapper().selectOne(new QueryWrapper<GameCost>().eq("card_id", cardId).eq("rank", currentRank + 1));
            if(gameCost == null){
                System.out.println("升级表数据异常");
                return false;
            }
            if(gameMyMoney.getMoneyNum()-gameCost.getCost()>=0){
                // 2.1 够->
                // 2.1.1 减去用户的货币数量，
                gameMyMoney.setMoneyNum(gameMyMoney.getMoneyNum()-gameCost.getCost());
                gameMyMoneyService.updateById(gameMyMoney);
                // 2.1.2 更新用户卡片等级的变化
                myCard.setCurrentRank(myCard.getCurrentRank()+1);
                baseMapper.updateById(myCard);
                // 2.1.3 记录用户的更新消耗情况
                GameRecordMyCard gameRecordMyCard = new GameRecordMyCard();
                gameRecordMyCard.setUserId(userId);
                gameRecordMyCard.setCardId(cardId);
                gameRecordMyCard.setCostMoney(gameCost.getCost());
                gameRecordMyCard.setCostCardNum(0);
                gameRecordMyCard.setToStar(myCard.getCurrentStar());
                gameRecordMyCard.setToRank(myCard.getCurrentRank()+1);
                gameRecordMyCard.setUpdateType(1);
                gameRecordMyCard.setCostType(1);
                return gameRecordMyCardService.save(gameRecordMyCard);

            }else {
                // 2.2 不够->
                // 2.2.1 继续讽刺
                return false;
            }
        }else {
            return false;
        }
    }


}
