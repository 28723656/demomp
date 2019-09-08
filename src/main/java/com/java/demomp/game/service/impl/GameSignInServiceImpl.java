package com.java.demomp.game.service.impl;

import com.java.demomp.game.entity.GameSignIn;
import com.java.demomp.game.mapper.GameSignInMapper;
import com.java.demomp.game.service.GameSignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-08
 */
@Service
public class GameSignInServiceImpl extends ServiceImpl<GameSignInMapper, GameSignIn> implements GameSignInService {

    /**
     * 获取今日奖励
     * @return
     */
    public GameSignIn findTodayReward() {
       return baseMapper.findTodayReward();
    }
}
