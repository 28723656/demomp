package com.java.demomp.admin.service.impl;

import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.entity.Role;
import com.java.demomp.admin.mapper.RoleMapper;
import com.java.demomp.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
                if (i < roleList.size()-1) {
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





}
