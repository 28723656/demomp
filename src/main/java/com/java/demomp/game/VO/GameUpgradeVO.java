package com.java.demomp.game.VO;

import com.java.demomp.game.entity.GameUpgrade;
import lombok.Data;

/**
 * @author 风往西边吹丶
 * @create 2019-08-26 22:43
 */
@Data
public class GameUpgradeVO  {

    /**
     * 卡片的id
     */
    private Integer id;

    // 最多10星啊，不能再多啦
    private Integer star1;
    private Integer star2;
    private Integer star3;
    private Integer star4;
    private Integer star5;
    private Integer star6;
    private Integer star7;
    private Integer star8;
    private Integer star9;
    private Integer star10;

}
