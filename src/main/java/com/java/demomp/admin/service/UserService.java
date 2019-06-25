package com.java.demomp.admin.service;

import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
public interface UserService extends IService<User> {

    List<UserRoleVO> getUserList();

    Integer addUser(UserRoleVO userRoleVO);

    Integer updateUser(UserRoleVO userRoleVO);

    Integer deleteUserById(Integer id);

    List<UserRoleVO> getUserByRole();
}
