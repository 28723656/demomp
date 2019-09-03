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
 * @since 2019-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_game_record_reward")
public class GameRecordReward extends Model<GameRecordReward> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer cardId;

    /**
     * 获得了多少个卡片
     */
    private Integer cardNums;

    /**
     * 抽奖的卡片类型
     */
    private String cardType;

    /**
     * 抽奖的次数，1还是10
     */
    private Integer cardTimes;

    /**
     * 消耗了多少
     */
    private Integer consumeNums;

    /**
     * 消耗的类型，key,gold，钻石
     */
    private Integer consumeType;

    /**
     * 抽奖的序号 1-10
     */
    private Integer rewardOrder;

    /**
     * 获得金币的数量
     */
    private Integer rewardCoinNums;

    /**
     * 获奖类型：1：卡片 2：金币
     */
    private Integer rewardType;

    /**
     * luckyid
     */
    private Integer luckyId;

    /**
     * 独一无二的标记，同批次的时候是相同的
     */
    private String uniqueMark;

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
