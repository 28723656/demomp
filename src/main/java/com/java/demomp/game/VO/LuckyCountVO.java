package com.java.demomp.game.VO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户做抽奖统计报表的类
 * @author 风往西边吹丶
 * @create 2019-09-03 21:59
 */
@Data
public class LuckyCountVO {


    // 卡片类型  SABCD
    private String cardType;

    // 卡片数量
    private Integer cardNum;

    // 获奖类型
    private Integer rewardType;

    // 获奖数量
    private Integer rewardNum;

    // 卡片名称
    private String cardName;

    // 获得金币数量
    private String coinNum;

    // 获奖日期
    private LocalDateTime rewardTime;

    // 每次抽奖独一无二的标记
    private String uniqueMark;
}
