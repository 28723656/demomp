package com.java.demomp.game.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
