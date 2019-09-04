package com.java.demomp.game.VO;

import lombok.Data;

/**
 * 一个临时的VO
 * @author 风往西边吹丶
 * @create 2019-09-04 11:08
 */
@Data
public class TempForCardDictionaryVO {

    // 卡片id
    private Integer cardId;
    // 每星需要卡片数量
    private Integer starNum;
    // 多少星
    private Integer star;
    // 没星需要多少等级
    private Integer rankNum;


    // 卡包的名称  (产出)
    private String luckyName;


}
