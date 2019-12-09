package com.java.demomp.star.entity;

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
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_star_trans")
public class StarTrans extends Model<StarTrans> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 用户
   */
  private Integer userId;

  /**
   * 其他某一个人
   */
  private Integer otherId;

  /**
   * 关联哪一个项目
   */
  private Integer projectId;

  /**
   * 交易类型 1-issue 2-问题  3-其他
   */
  private Integer transType;

  /**
   * 交易方向（默认是user_id花钱转给other_id）1-user->other  2-other->user
   */
  private Integer transToType;

  /**
   * 交易数量
   */
  private Integer transNum;

  /**
   * 描述
   */
  private String description;

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
