package com.java.demomp.game.VO;

import com.java.demomp.game.entity.GameCard;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 卡片字典VO
 * @author 风往西边吹丶
 * @create 2019-09-04 11:02
 */
@Data
public class CardDictionaryVO extends GameCard {

    // 升星消耗卡牌
    private List<Integer> starCostNum;

    // 每星可升等级
    private List<Integer> starRankNum;

    // 可选属性 每一个星级
    private List<Integer> star;

    // 满级效果
    private BigDecimal incCoinFull;
    private BigDecimal incExperienceFull;
    private BigDecimal lowPercentFull;
    private BigDecimal topPercentFull;

    //获取途径
    private List<String> rewardPlace;


}
