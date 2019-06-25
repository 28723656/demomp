package com.java.demomp.admin.service;

import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.Role;
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
public interface RoleService extends IService<Role> {

    List<RoleMenuVO> getRoleList();

    List<Role> getRoles();

    Integer addRole(RoleMenuVO roleMenuVO);

    Integer updateRole(RoleMenuVO roleMenuVO);

    Integer deleteRole(Integer id);

    List<RoleMenuVO> getRoleByMenu();
}
