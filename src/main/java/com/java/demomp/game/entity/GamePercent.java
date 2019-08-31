package com.java.demomp.game.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

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
 * @since 2019-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_game_percent")
public class GamePercent extends Model<GamePercent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 抽中的个数
     */
    private Integer nums;

    /**
     * 概率
     */
    private BigDecimal percent;

    private Integer cardId;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTimes;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTimes;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
