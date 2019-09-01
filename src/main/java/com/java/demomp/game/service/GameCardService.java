package com.java.demomp.game.service;

import com.java.demomp.game.entity.GameCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.util.StatusCode;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
public interface GameCardService extends IService<GameCard> {

    int deleteCardByCardId(Integer cardId);

    void saveCard(GameCard gameCard);

    List<Map<String, Object>> showMyCard(Integer userId);
}
