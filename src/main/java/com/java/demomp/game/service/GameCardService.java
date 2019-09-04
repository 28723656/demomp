package com.java.demomp.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.game.VO.CardDictionaryVO;
import com.java.demomp.game.VO.GameMyCardVO;
import com.java.demomp.game.entity.GameCard;

import java.util.List;

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

    List<GameMyCardVO> showMyCard(Integer userId);

    List<CardDictionaryVO> showCardDictionary();
}
