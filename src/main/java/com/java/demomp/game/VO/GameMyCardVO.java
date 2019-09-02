package com.java.demomp.game.VO;

/**
 * @author 风往西边吹丶
 * @create 2019-09-02 17:49
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * 这个实体用来封装升级卡片的属性
 */
@Data
public class GameMyCardVO  {

    // myCard 卡片id
    private Integer cardId;

    //  myCard 卡片数量
    private Integer cardNum;

    // myCard 当前星级
    private Integer currentStar;

    // myCard 当前等级
    private Integer currentRank;


    // upgrade 升星需要多少卡片
    private Integer updateStarNeedNum;



    // card 卡片是哪个技能
    private Integer skill;
    // card 是什么类型的卡片
    private String cardType;
    // card 卡片的最高星级
    private Integer topStar;
    // card 卡片的名字
    private String cardName;



    // cost 升级下一级需要多少金币
    private Integer cost;

    // cost 当前星级的最高等级为多少
    private Integer starTopRank;


    // cost 当前升级增加多少金币  / 下一级
    private BigDecimal incCoin;
    private BigDecimal incCoinNext;

    // cost 当前升级增加多少exp  / 下一级
    private BigDecimal incExperience;
    private BigDecimal incExperienceNext;

    // cost 当前升级增加多少钥匙 /下一级
    private BigDecimal incKeyLow;
    private BigDecimal incKeyTop;
    private BigDecimal incKeyLowNext;
    private BigDecimal incKeyTopNext;



}
