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
* @since 2019-09-02
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("t_game_record_my_card")
    public class GameRecordMyCard extends Model<GameRecordMyCard> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer cardId;

    private Integer costMoney;

            /**
            * 花费了多少张卡片
            */
    private Integer costCardNum;

            /**
            * 升级到了多少星
            */
    private Integer toStar;

            /**
            * 升级到了多少级
            */
    private Integer toRank;

            /**
            * 1-升级 2-升星
            */
    private Integer updateType;

            /**
            * 花费类型1-金币 2-钻石 3-抵用券（后面可能会加新东西）
            */
    private Integer costType;

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
