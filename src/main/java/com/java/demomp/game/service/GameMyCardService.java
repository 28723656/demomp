package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameCost;
import com.java.demomp.game.entity.GameMyCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.game.entity.GameRewardDay;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-31
 */
public interface GameMyCardService extends IService<GameMyCard> {

    boolean updateCardStar(Integer userId, Integer cardId, Integer updateType);

    List<GameRewardDay> getEveryDayReward();

    GameCost getSkillTotal(Integer userId);
}
