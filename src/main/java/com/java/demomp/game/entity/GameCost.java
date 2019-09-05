package com.java.demomp.game.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_game_cost")
public class GameCost extends Model<GameCost> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 卡片id
     */
    private Integer cardId;

    /**
     * 卡片等级
     */
    private Integer rank;

    /**
     * 升级的花费
     */
    private Integer cost;

    /**
     * 当前星级
     */
    private Integer star;

    /**
     * 增加的金币
     */
    private BigDecimal incCoin;

    /**
     * 增加经验
     */
    private BigDecimal incExperience;

    /**
     * 最低的概率
     */
    private BigDecimal lowPercent;

    /**
     * 最高的概率
     */
    private BigDecimal topPercent;

    /**
     * 概率数学期望
     */
    private BigDecimal hopePercent;

    @TableLogic
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

/*    @TableField(exist = false)
    // 临时属性，不是表的属性
    private Integer skill;*/


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
