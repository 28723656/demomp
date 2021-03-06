package com.java.demomp.plan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_plan")
public class Plan extends Model<Plan> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 实际完成时间,因为我要设置空值,使不得，使不得
     */
    //@TableField(strategy=FieldStrategy.IGNORED)
    private LocalDateTime actualTime;

    /**
     * 等级D->S
     */
    private Integer rank;

    /**
     * 百分比
     */
    private Double percent;

    /**
     * 是否已经完成   0-未完成  1-完成  2-不做了，假完成
     */
    private Integer finished;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 是否为通用计划
     */
    private Integer base;

    /**
     * 类型：当日、本周、当月、年
     */
    private Integer type;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 如果真爱有颜色
     */
    private String color;

    /**
     * 用户id
     */

    private Integer userId;

    /**
     * 逻辑删除
     */
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
