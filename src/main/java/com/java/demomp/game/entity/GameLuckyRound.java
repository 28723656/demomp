package com.java.demomp.game.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableLogic;
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
* @since 2019-08-30
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("t_game_lucky_round")
    public class GameLuckyRound extends Model<GameLuckyRound> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 基本轮次
            */
    private Integer roundCount;

            /**
            * 真正的轮次
            */
    private Integer realRound;

        @TableLogic
    private Integer deleted;

    private LocalDateTime createTimes;

    private LocalDateTime updateTimes;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
