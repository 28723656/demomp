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
 * @since 2019-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_game_config")
public class GameConfig extends Model<GameConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cardId;

    /**
     * 用于判断是否配置完了后，修改的最高星级
     */
    private Integer topStar;

    private Integer oneStar;

    private Integer eachStar;

    private Integer fullRank;

    private Integer oneCost;

    private Integer eachCost;

    private Integer fullCost;

    private Integer fullCostAll;

    private BigDecimal oneIncCoin;

    private BigDecimal eachIncCoin;

    private BigDecimal fullIncCoin;

    private BigDecimal oneIncExperience;

    private BigDecimal eachIncExperience;

    private BigDecimal fullIncExperience;

    private BigDecimal oneLowPercent;

    private BigDecimal eachLowPercent;

    private BigDecimal fullLowPercent;

    private BigDecimal oneTopPercent;

    private BigDecimal eachTopPercent;

    private BigDecimal fullTopPercent;

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
