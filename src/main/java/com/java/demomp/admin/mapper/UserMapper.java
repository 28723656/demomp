package com.java.demomp.admin.mapper;

import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select u.id,u.phone,u.nick_name,u.description,ur.role_id from t_user u ,t_user_role ur where u.id = ur.user_id and u.deleted = 0 and ur.deleted = 0")
    List<UserRoleVO> getUserList();

    @Select("SELECT  u.id,u.phone,u.description,u.nick_name,ur.role_id from t_user u, t_user_role ur where  ur.user_id = u.id and u.deleted =0 and ur.deleted =0")
    List<UserRoleVO> getUserByRole();
}
