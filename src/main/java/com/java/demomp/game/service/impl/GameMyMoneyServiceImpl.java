package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameMyMoney;
import com.java.demomp.game.mapper.GameMyMoneyMapper;
import com.java.demomp.game.service.GameMyMoneyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-31
 */
@Service
public class GameMyMoneyServiceImpl extends ServiceImpl<GameMyMoneyMapper, GameMyMoney> implements GameMyMoneyService {

    /**
     * 根据用户id初始化用户的货币
     * @param
     * @return
     */
    public boolean initUserCoin(Integer userId) {
        GameMyMoney gameMyMoney1 = initEntity(userId,3000,1);
        GameMyMoney gameMyMoney2 = initEntity(userId,600,2);
        GameMyMoney gameMyMoney3 = initEntity(userId,50,3);
        int insert1 = baseMapper.insert(gameMyMoney1);
        int insert2 = baseMapper.insert(gameMyMoney2);
        int insert3 =baseMapper.insert(gameMyMoney3);
        return insert3>0;

    }


    /**
     * 彩蛋福利
     * @param userId
     * @param clickRank
     */
    public void clickReward(Integer userId, Integer clickRank) {
        int initCoin = 1000;
        int initDiamond = 200;
        int initKey = 50;
        List<GameMyMoney> myMoneyList = baseMapper.selectList(new QueryWrapper<GameMyMoney>().eq("user_id", userId));
        for(int i=0;i<myMoneyList.size();i++){
            GameMyMoney myMoney = myMoneyList.get(i);
            if(myMoney.getType() ==1){
                myMoney.setMoneyNum(myMoney.getMoneyNum()+(int)(initCoin*(0.8+0.2*clickRank)));
            }else if(myMoney.getType() ==2){
                myMoney.setMoneyNum(myMoney.getMoneyNum()+(int)(initDiamond*(0.8+0.2*clickRank)));
            }else if(myMoney.getType() ==3){
                myMoney.setMoneyNum(myMoney.getMoneyNum()+(int)(initKey*(0.8+0.2*clickRank)));
            }
            baseMapper.updateById(myMoney);
        }
    }

    // 用于初始化
    public GameMyMoney initEntity(Integer userId,Integer moneyNum,Integer type){
        GameMyMoney myMoney = new GameMyMoney();
        myMoney.setUserId(userId);
        myMoney.setMoneyNum(moneyNum);
        myMoney.setType(type);
        return myMoney;
    }
}
