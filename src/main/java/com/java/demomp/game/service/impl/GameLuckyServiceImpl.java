package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.game.entity.GameLucky;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.java.demomp.game.entity.GameLuckyRound;
import com.java.demomp.game.mapper.GameLuckyMapper;
import com.java.demomp.game.service.GameLuckyConfigService;
import com.java.demomp.game.service.GameLuckyRoundService;
import com.java.demomp.game.service.GameLuckyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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

    // 删除
    public boolean deleteLucky(Integer id) {
       // 1.删除t_game_lucky
        int deleteNums1 = baseMapper.deleteById(id);

        // 2.删除t_game_lucky_config
        int deleteNums2 = gameLuckyConfigService.getBaseMapper().delete(new UpdateWrapper<GameLuckyConfig>().eq("lucky_id", id));

        // 3.删除t_game_lucky_round
        int deleteNums3 = gameLuckyRoundService.getBaseMapper().delete(new UpdateWrapper<GameLuckyRound>().eq("lucky_id",id));

        return deleteNums1> 0;
    }
}
