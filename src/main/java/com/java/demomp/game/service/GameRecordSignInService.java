package com.java.demomp.game.service;

import com.java.demomp.game.VO.RecordSignInVO;
import com.java.demomp.game.entity.GameRecordSignIn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.game.entity.GameSignIn;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-08
 */
public interface GameRecordSignInService extends IService<GameRecordSignIn> {

    GameSignIn getSigInReward(Integer userId);

    List<RecordSignInVO> getDaysRecord(Integer day);
}
