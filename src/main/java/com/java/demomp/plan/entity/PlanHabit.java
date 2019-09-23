package com.java.demomp.plan.entity;

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
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_plan_habit")
public class PlanHabit extends Model<PlanHabit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    /**
     * 习惯名称
     */
    private String name;

    /**
     * 已经坚持的天数
     */
    private Integer keepDays;

    /**
     * 实际坚持的天数
     */
    private Integer realKeepDays;

    /**
     * 所有的天数
     */
    private Integer allDays;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 类型：1-完美（一天也没有拉下）
     *  2-不完美（有过中途放弃）
     *  3-已经放弃了
     *  4-通关，完美
     *  5-通关，但是不完美
     */
    private Integer type;

    /**
     * 今日是否完成 1-完成 0-未完成
     */
    private Integer todayFinish;

    /**
     * 今日完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 未完成的天数
     */
    private Integer unfinishedDays;

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
