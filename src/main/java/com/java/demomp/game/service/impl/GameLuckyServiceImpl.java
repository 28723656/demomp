package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.game.entity.*;
import com.java.demomp.game.mapper.GameLuckyMapper;
import com.java.demomp.game.service.*;
import com.java.demomp.util.RandomUtil;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-29
 */
@Service
public class GameLuckyServiceImpl extends ServiceImpl<GameLuckyMapper, GameLucky> implements GameLuckyService {

    @Autowired
    GameLuckyConfigService gameLuckyConfigService;

    @Autowired
    GameLuckyRoundService gameLuckyRoundService;

    @Autowired
    GameMyMoneyService gameMyMoneyService;

    @Autowired
    GamePercentService gamePercentService;

    @Autowired
    GameMyCardService gameMyCardService;

    @Autowired
    GameRecordRewardService gameRecordRewardService;

    // 删除
    public boolean deleteLucky(Integer id) {
        // 1.删除t_game_lucky
        int deleteNums1 = baseMapper.deleteById(id);

        // 2.删除t_game_lucky_config
        int deleteNums2 = gameLuckyConfigService.getBaseMapper().delete(new UpdateWrapper<GameLuckyConfig>().eq("lucky_id", id));

        // 3.删除t_game_lucky_round
        int deleteNums3 = gameLuckyRoundService.getBaseMapper().delete(new UpdateWrapper<GameLuckyRound>().eq("lucky_id", id));

        return deleteNums1 > 0;
    }


    // 开箱   link start
    @Transactional
    public int openLucky(Integer userId, Integer luckyId, Integer openTimes) {

        // 1.判断用户货币是否足够，注意：加上事务
        GameLucky gameLucky = baseMapper.selectById(luckyId);
        // 花费
        int cost = 0;
        // 花费类型  1-金币 2-钻石 3-key
        int costType = 1;
        if (openTimes == 1) {
            cost = gameLucky.getOnceCost();
        } else if (openTimes == 10) {
            cost = gameLucky.getTenTimesCost();
        }
        costType = gameLucky.getCostType();

        // 我的货币实体
        List<GameMyMoney> myMoneyList = gameMyMoneyService.getBaseMapper().selectList(new QueryWrapper<GameMyMoney>().eq("user_id", userId));
        GameMyMoney myMoneyEntity = new GameMyMoney();
        for (int i = 0; i < myMoneyList.size(); i++) {
            GameMyMoney tempGameMyMoney = myMoneyList.get(i);
            if (tempGameMyMoney.getType() == costType) {
                myMoneyEntity = tempGameMyMoney;
                break;
            }

        }
        // 比较货币是否足够
        if (myMoneyEntity.getMoneyNum() >= cost) {
            // 2.足够->

            // 2.1 减去货币：根据luckyId拿到数据，然后根据openTimes减去相应的货币(减去货币是一次性算的)
            myMoneyEntity.setMoneyNum(myMoneyEntity.getMoneyNum() - cost);
            gameMyMoneyService.getBaseMapper().updateById(myMoneyEntity);

            for(int n = 0;n<openTimes;n++){
                // 2.2 查找概率列表：根据luckyId找到概率列表list数据
                // 2.2.1 根据round表里面找到最新的概率数据,即确定round_id
                GameLuckyRound gameLuckyRound = gameLuckyRoundService.getBaseMapper().selectOne(new QueryWrapper<GameLuckyRound>().eq("lucky_id", luckyId).orderByDesc("round_count").last("limit 1"));
                List<GameLuckyConfig> gameLuckyConfigList = gameLuckyConfigService.getBaseMapper().selectList(new QueryWrapper<GameLuckyConfig>().eq("lucky_id", luckyId).eq("round_id", gameLuckyRound.getId()));

                // 2.3 算出结果：生成随机数，确定这个数落在概率表的哪里

                // 下面这个是中奖的实体
                int tempNum = 0; // 概率累加计数
                GameLuckyConfig luckyEntity = new GameLuckyConfig();
                for (int i = 0; i < gameLuckyConfigList.size(); i++) {
                    Integer randomNum = RandomUtil.getRandomFromTo(1, 1000000);
                    // 实体的概率  *10000，化为整数
                    int percent = 0;
                    luckyEntity = gameLuckyConfigList.get(i);
                    percent = luckyEntity.getPercent().multiply(BigDecimal.valueOf(10000.0)).intValue();
                    tempNum += percent;
                    if (tempNum >= randomNum) {
                        // 中了相应的奖项
                        break;
                    }
                }

                // 2.3.1 具体中了几个：根据percent表中，继续查询是中了几个
                List<GamePercent> gamePercentList = gamePercentService.list(new QueryWrapper<GamePercent>().eq("card_id", luckyEntity.getCardId()));
                // 获得卡片的数量
                int tempNum2 = 0; // 概率累加计数
                GamePercent percentEntity = new GamePercent();
                for (int i = 0; i < gamePercentList.size(); i++) {
                    Integer randomNum = RandomUtil.getRandomFromTo(1, 1000000);
                    int percent = 0;
                    percentEntity = gamePercentList.get(i);
                    percent = percentEntity.getPercent().multiply(BigDecimal.valueOf(10000.0)).intValue();
                    tempNum2 += percent;
                    if (tempNum2 >= randomNum) {
                        // 中了相应的奖项
                        break;
                    }
                }

                // 2.4 写入数据：1.得到获得的卡片集合，把获得结果写入my_card表里面
                // 2.4.1 查询这个表中用户是否有记录，有->更新  没有->插入
                GameMyCard gameMyCard = gameMyCardService.getBaseMapper().selectOne(new QueryWrapper<GameMyCard>().eq("user_id", userId).eq("card_id", percentEntity.getCardId()).last("limit 1"));
                if (gameMyCard == null) {
                    // 没有记录，插入数据
                    GameMyCard tempGameMyCard = new GameMyCard();
                    tempGameMyCard.setUserId(userId);
                    tempGameMyCard.setCardId(percentEntity.getCardId());
                    tempGameMyCard.setNum(percentEntity.getNums());
                    tempGameMyCard.setCurrentStar(0);
                    tempGameMyCard.setCurrentRank(0);
                    boolean b = gameMyCardService.save(tempGameMyCard);
                }else {
                    gameMyCard.setNum(gameMyCard.getNum()+percentEntity.getNums());
                    boolean b = gameMyCardService.updateById(gameMyCard);
                }
                //  2.把结果写入record_reward表里面
                GameRecordReward gameRecordReward = new GameRecordReward();
                gameRecordReward.setUserId(userId);
                gameRecordReward.setCardId(percentEntity.getCardId());
                gameRecordReward.setCardNums(percentEntity.getNums());
                gameRecordReward.setCardType(luckyEntity.getCardType());
                gameRecordReward.setCardTimes(openTimes);
                gameRecordReward.setRewardOrder(n+1);
                gameRecordReward.setConsumeNums(cost);
                gameRecordReward.setConsumeType(costType);
                gameRecordReward.setRewardCoinNums(0);
                gameRecordReward.setRewardType(1);
                boolean b = gameRecordRewardService.save(gameRecordReward);
            }
            return StatusCode.OK;
        } else {
            // 3.不足够 ->
            return StatusCode.NOMONEY;
            // 3.1 返回一个货币不足的状态
        }
    }
}
