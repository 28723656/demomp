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
* @since 2019-09-08
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("t_game_sign_in")
    public class GameSignIn extends Model<GameSignIn> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 签到日期
            */
    private LocalDateTime dayTime;

    private String name;

    private String description;

    private Integer num;

            /**
            * 如果是获取卡片的话
            */
    private Integer cardId;

            /**
            * 获得道具类型
            */
    private Integer type;

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
