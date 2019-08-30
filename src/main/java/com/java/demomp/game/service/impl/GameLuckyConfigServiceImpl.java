package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.VO.GameLuckyConfigVO;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.java.demomp.game.entity.GameLuckyRound;
import com.java.demomp.game.mapper.GameLuckyConfigMapper;
import com.java.demomp.game.service.GameLuckyConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.game.service.GameLuckyRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-30
 */
@Service
public class GameLuckyConfigServiceImpl extends ServiceImpl<GameLuckyConfigMapper, GameLuckyConfig> implements GameLuckyConfigService {


    @Autowired
    GameLuckyRoundService gameLuckyRoundService;

    @Autowired
    GameLuckyConfigService gameLuckyConfigService;

    /**
     * 批量添加配置
     * @param configList
     * @return
     */
    @Override
    public boolean addLuckyConfig(List<GameLuckyConfig> configList) {

       int luckyId =  configList.get(0).getLuckyId();
        // 有几个步骤
        // 1.确定轮次，然后把round_id逐个放入对象中
        int roundId = 1;
        GameLuckyRound roundCount = gameLuckyRoundService.getOne(new QueryWrapper<GameLuckyRound>().eq("lucky_id",luckyId).orderByDesc("round_count").last("limit 1"));
        if(roundCount == null){
            // 首次添加的话，找到最大的id值，并+1
            GameLuckyRound maxIdEntity = gameLuckyRoundService.getOne(new QueryWrapper<GameLuckyRound>().orderByDesc("id").last("limit 1"));
            if(maxIdEntity !=null){
                roundId = maxIdEntity.getId()+1;
            }
            GameLuckyRound gameLuckyRound = new GameLuckyRound();
            gameLuckyRound.setRoundCount(1);
            gameLuckyRound.setLuckyId(luckyId);
            gameLuckyRoundService.save(gameLuckyRound);
        }else {
            GameLuckyRound gameLuckyRound = new GameLuckyRound();
            gameLuckyRound.setRoundCount(roundCount.getRoundCount()+1);
            gameLuckyRound.setLuckyId(luckyId);
            gameLuckyRoundService.save(gameLuckyRound);
            roundId = gameLuckyRound.getId();
        }

        // 对象直接是引用，所以可以这样赋值
        for(int i=0;i<configList.size();i++){
            GameLuckyConfig tempGameLuckyConfig = configList.get(i);
            tempGameLuckyConfig.setRoundId(roundId);
        }
        // 然后批量插入就行了
        boolean b = gameLuckyConfigService.saveBatch(configList);
        return b;
    }

    /**
     * 获取最新的一次的配置
     * @return
     */
    public List<GameLuckyConfig> getNewestLuckyConfig(Integer luckyId) {
        // 获取最后一次的 round_id
        GameLuckyRound gameLuckyRound = gameLuckyRoundService.getOne(new QueryWrapper<GameLuckyRound>().eq("lucky_id",luckyId).orderByDesc("round_count").last("limit 1"));

        List<GameLuckyConfig> list = baseMapper.selectList(new QueryWrapper<GameLuckyConfig>().eq("round_id", gameLuckyRound.getId()).eq("lucky_id", luckyId));
        //  List<GameLuckyConfigVO> list = baseMapper.getNewestLuckyConfig(gameLuckyRound.getId(),luckyId);
        return list;
    }

}
