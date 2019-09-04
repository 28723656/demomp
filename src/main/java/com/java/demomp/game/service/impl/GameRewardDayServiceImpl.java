package com.java.demomp.game.service.impl;

import com.java.demomp.game.entity.GameRewardDay;
import com.java.demomp.game.mapper.GameRewardDayMapper;
import com.java.demomp.game.service.GameRewardDayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-04
 */
@Service
public class GameRewardDayServiceImpl extends ServiceImpl<GameRewardDayMapper, GameRewardDay> implements GameRewardDayService {

    public GameRewardDay getRewardDay(Integer userId) {
        return baseMapper.getRewardDay(userId);
    }
}
