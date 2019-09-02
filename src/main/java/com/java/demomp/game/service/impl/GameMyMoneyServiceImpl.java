package com.java.demomp.game.service.impl;

import com.java.demomp.game.entity.GameMyMoney;
import com.java.demomp.game.mapper.GameMyMoneyMapper;
import com.java.demomp.game.service.GameMyMoneyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    // 用于初始化
    public GameMyMoney initEntity(Integer userId,Integer moneyNum,Integer type){
        GameMyMoney myMoney = new GameMyMoney();
        myMoney.setUserId(userId);
        myMoney.setMoneyNum(moneyNum);
        myMoney.setType(type);
        return myMoney;
    }
}
