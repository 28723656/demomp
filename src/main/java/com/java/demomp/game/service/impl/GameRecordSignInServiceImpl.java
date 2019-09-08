package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.VO.RecordSignInVO;
import com.java.demomp.game.entity.GameMyCard;
import com.java.demomp.game.entity.GameMyMoney;
import com.java.demomp.game.entity.GameRecordSignIn;
import com.java.demomp.game.entity.GameSignIn;
import com.java.demomp.game.mapper.GameRecordSignInMapper;
import com.java.demomp.game.service.GameMyCardService;
import com.java.demomp.game.service.GameMyMoneyService;
import com.java.demomp.game.service.GameRecordSignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.game.service.GameSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-08
 */
@Service
public class GameRecordSignInServiceImpl extends ServiceImpl<GameRecordSignInMapper, GameRecordSignIn> implements GameRecordSignInService {

    @Autowired
    GameSignInService gameSignInService;

    @Autowired
    GameMyMoneyService gameMyMoneyService;

    @Autowired
    GameMyCardService gameMyCardService;
    /**
     * 获取用户今日签到奖励
     * @param userId
     * @return
     */
    public GameSignIn getSigInReward(Integer userId) {
        // 1.判断用户今日是否已经领取，已经领取则返回空
       GameRecordSignIn gameRecordSignIn = baseMapper.checkSignIn(userId);
       // 2.如果为空，表示用户今天没有登录
       if(gameRecordSignIn == null){
           // 3.查询登录奖励
        GameSignIn gameSignIn = gameSignInService.findTodayReward();
        if(gameSignIn == null){
            // 4.如果没有找到登录奖励，表示数据库没有数据
            return null;
        }else {
            // 5.当天第一次登录，得到奖励
            // 5.1. 存放获奖记录，
            GameRecordSignIn record = new GameRecordSignIn();
            record.setUserId(userId);
            record.setSigninId(gameSignIn.getId());
            baseMapper.insert(record);
            // 5.2.获得实际物品
            if(gameSignIn.getType() == 1 ||gameSignIn.getType() == 2 ||gameSignIn.getType() == 3  ){
                // 5.2.1 如果是货币
                GameMyMoney gameMyMoney = gameMyMoneyService.getBaseMapper().selectOne(new QueryWrapper<GameMyMoney>().eq("user_id", userId).eq("type", gameSignIn.getType()));
                gameMyMoney.setMoneyNum(gameMyMoney.getMoneyNum()+gameSignIn.getNum());
                gameMyMoneyService.updateById(gameMyMoney);
            }else if(gameSignIn.getType() == 4){
                // 5.2.2 如果是卡片
                GameMyCard myCard = gameMyCardService.getBaseMapper().selectOne(new QueryWrapper<GameMyCard>().eq("user_id", userId).eq("card_id", gameSignIn.getCardId()));
                // 5.2.2.1 如果用户没有这个卡片
                if(myCard == null){
                    GameMyCard newCard = new GameMyCard();
                    newCard.setUserId(userId);
                    newCard.setCardId(gameSignIn.getCardId());
                    newCard.setNum(gameSignIn.getNum());
                    newCard.setCurrentStar(0);
                    newCard.setCurrentRank(0);
                    gameMyCardService.save(newCard);
                }else {
                    // 5.2.2.2 有这个卡片，更新数量就好了
                    myCard.setNum(myCard.getNum()+gameSignIn.getNum());
                    gameMyCardService.updateById(myCard);
                }
            }
            return gameSignIn;
        }
       }else {
           // 6.用户已经登录过了
           return null;
       }
    }

    /**
     * 获取全部用户几天内登陆情况
     * @param day
     * @return
     */
    public List<RecordSignInVO> getDaysRecord(Integer day) {
        return baseMapper.getDaysRecord(day);
    }
}
