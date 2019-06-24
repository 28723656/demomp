package com.java.demomp.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.entity.Role;
import com.java.demomp.admin.entity.RoleMenu;
import com.java.demomp.admin.entity.UserRole;
import com.java.demomp.admin.mapper.RoleMapper;
import com.java.demomp.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    public List<RoleMenuVO> getRoleList() {
        List<RoleMenuVO> roleList = baseMapper.getRoleList();

        // 用于存放的
        List<RoleMenuVO> resultRoleList = new ArrayList<>();
        List<Integer> menuIdList = new ArrayList<>();

        if (roleList != null && roleList.size() > 0) {

            for (int i = 0; i < roleList.size(); i++) {
                RoleMenuVO roleMenuVO = roleList.get(i);
                Integer resultId = roleMenuVO.getId();
                Integer nextId = null;
                if (i < roleList.size() - 1) {
                    nextId = roleList.get(i + 1).getId();
                }
                menuIdList.add(roleMenuVO.getTempMenuId());
                // 如果两个id不相同
                if (nextId != resultId) {
                    roleMenuVO.setMenuId(menuIdList);
                    resultRoleList.add(roleMenuVO);
                    menuIdList = new ArrayList<>();
                }
            }
        }

        return resultRoleList;

    }

    public List<Role> getRoles() {
        List<Role> roles = baseMapper.selectList(null);
        return roles;
    }


    /**
     * 添加角色
     *
     * @param roleMenuVO
     * @return
     */
    public Integer addRole(RoleMenuVO roleMenuVO) {
        // 现在t_role表中添加角色
        Role role = new Role();
        role.setName(roleMenuVO.getName());
        role.setDescription(roleMenuVO.getDescription());
        boolean b = role.insert();

        // 在t_role_menu表中添加关系(有多个关系)
        List<Integer> menuIdList = roleMenuVO.getMenuId();
        for (int i = 0; i < menuIdList.size(); i++) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getId());
            roleMenu.setMenuId(menuIdList.get(i));
            roleMenu.insert();
        }

        return 1;
    }

    /**
     * 修改角色
     *
     * @param roleMenuVO
     * @return
     */
    public Integer updateRole(RoleMenuVO roleMenuVO) {
        // 修改t_role表
        Role role = new Role();
        role.setId(roleMenuVO.getId());
        role.setName(roleMenuVO.getName());
        role.setDescription(roleMenuVO.getDescription());
        boolean b = role.updateById();

        // 在t_role_menu表中添加关系(有多个关系)
        List<Integer> menuIdList = roleMenuVO.getMenuId();
        // 先删了再添加，简单粗暴
        new RoleMenu().delete(new QueryWrapper<RoleMenu>().eq("role_id",roleMenuVO.getId()));
        for (int i = 0; i < menuIdList.size(); i++) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getId());
            roleMenu.setMenuId(menuIdList.get(i));
            roleMenu.insert();
        }
        return 1;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    public Integer deleteRole(Integer id) {
       // 如果角色下面有用户，禁止删除
        UserRole userRole = new UserRole();
        List<UserRole> userRoleList = userRole.selectList(new QueryWrapper<UserRole>().eq("role_id", id));
        // 如果角色下面有用户
        if(userRoleList != null && userRoleList.size() > 0){
            return 0;
        }else{
            // 删除
            boolean b = new Role().deleteById(id);
            // 顺便还要把RoleMenu的关系给删除了
            boolean deleteNum = new RoleMenu().delete(new QueryWrapper<RoleMenu>().eq("role_id", id));;
            if(b){
                return 1;
            }else {
                return 0;
            }
        }
    }


}
