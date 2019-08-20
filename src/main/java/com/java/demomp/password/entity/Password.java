package com.java.demomp.password.entity;

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
 * @since 2019-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_password")
public class Password extends Model<Password> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 加密天数
     */
    private Integer days;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 开始日期
     */
    @TableField("begin_date")
    private LocalDateTime beginDate;

    /**
     * 结束日期
     */
    @TableField("end_date")
    private LocalDateTime endDate;

    /**
     * 解密后公布密码
     */
    @TableField("real_password")
    private String realPassword;

    /**
     * 暂时用于加密的一个东西，中间介质，没什么作用
     */
    private String idWorker;


    /**
     * 用户Id
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
