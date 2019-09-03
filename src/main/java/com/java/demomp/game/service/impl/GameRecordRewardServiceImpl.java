package com.java.demomp.game.service.impl;

import com.java.demomp.game.VO.LuckyCountVO;
import com.java.demomp.game.VO.RecordRewardCardVO;
import com.java.demomp.game.entity.GameRecordReward;
import com.java.demomp.game.mapper.GameRecordRewardMapper;
import com.java.demomp.game.service.GameRecordRewardService;
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
public class GameRecordRewardServiceImpl extends ServiceImpl<GameRecordRewardMapper, GameRecordReward> implements GameRecordRewardService {

    public List<RecordRewardCardVO> getEntityAndCardName(String idStr) {
        return baseMapper.getEntityAndCardName(idStr);
    }


    /**
     * 统计最近几个月来 每种卡片获得的数量总和
     * @param userId
     * @param monthCount
     * @return
     */
    public List<LuckyCountVO> statisticsCardType(Integer userId, Integer monthCount) {
        return baseMapper.statisticsCardType(userId,monthCount);
    }

    /**
     * 统计最近几个月来 货币总和
     * @param userId
     * @param monthCount
     * @return
     */
    public List<LuckyCountVO> statisticsCardAndCoin(Integer userId, Integer monthCount) {
        return baseMapper.statisticsCardAndCoin(userId,monthCount);
    }

    /**
     * 统计用户最近count条记录
     * @param userId
     * @param count
     * @return
     */
    public List<LuckyCountVO> statisticsLatestRecord(Integer userId, Integer count) {
        return baseMapper.statisticsLatestRecord(userId,count);
    }
}
