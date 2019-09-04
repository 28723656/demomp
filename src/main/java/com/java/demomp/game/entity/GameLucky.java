package com.java.demomp.game.entity;

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
 * @since 2019-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_game_lucky")
public class GameLucky extends Model<GameLucky> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 抽奖类型1-免费 2-普通 3-高级 4-至尊
     */
    private Integer type;

    /**
     * 卡包描述
     */
    private String description;

    /**
     * 卡包名称
     */
    private String name;

    /**
     * 产出
     */
    private String output;

    /**
     * 一次花费
     */
    private Integer onceCost;

    /**
     * 10次花费
     */
    private Integer tenTimesCost;

    /**
     * 花费的类型1-金币 2-钻石 3-钥匙
     */
    private Integer costType;

    /**
     * 是否开启这个抽奖 1-开启  0-不开启
     */
    private Integer open;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
