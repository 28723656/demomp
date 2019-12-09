package com.java.demomp.star.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.java.demomp.admin.entity.User;
import com.java.demomp.star.entity.StarTrans;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.demomp.star.vo.StarTransUserVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-12-09
 */
public interface StarTransMapper extends BaseMapper<StarTrans> {

  @Select("SELECT t.*, u2.nick_name AS otherName FROM t_star_trans t LEFT JOIN t_user u ON t.user_id = u.id  LEFT JOIN t_user u2 ON t.other_id = u2.id where  t.user_id = #{userId}")
  List<StarTransUserVO> getTransListByUserId(@Param("userId") Integer userId);

  @Select("select u.* from t_user_role ur left join t_user u on ur.user_id = u.id where ur.role_id = 11")
  List<User> getCanTransUser();
}
