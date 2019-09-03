package com.java.demomp.game.service;

import com.java.demomp.game.VO.LuckyCountVO;
import com.java.demomp.game.VO.RecordRewardCardVO;
import com.java.demomp.game.entity.GameRecordReward;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-31
 */
public interface GameRecordRewardService extends IService<GameRecordReward> {

    List<RecordRewardCardVO> getEntityAndCardName(String idStr);

    List<LuckyCountVO> statisticsCardType(Integer userId, Integer monthCount);

    List<LuckyCountVO> statisticsCardAndCoin(Integer userId, Integer monthCount);

    List<LuckyCountVO> statisticsLatestRecord(Integer userId, Integer count);
}
