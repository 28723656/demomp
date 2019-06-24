package com.java.demomp.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.entity.Menu;
import com.java.demomp.admin.entity.RoleMenu;
import com.java.demomp.admin.mapper.MenuMapper;
import com.java.demomp.admin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    public List<Menu> getMenuList() {
      return  baseMapper.selectList(null);
    }

    /**
     * 添加菜单
     * @param roleMenuVO
     * @return
     */
    public Integer addMenu(RoleMenuVO roleMenuVO) {
        Menu menu = new Menu();
        menu.setName(roleMenuVO.getName());
        menu.setDescription(roleMenuVO.getDescription());
        boolean insert = menu.insert();
        return insert?1:0;
    }

    /**
     * 更新菜单
     * @param roleMenuVO
     * @return
     */
    public Integer updateMenu(RoleMenuVO roleMenuVO) {
        Menu menu = new Menu();
        menu.setId(roleMenuVO.getId());
        menu.setName(roleMenuVO.getName());
        menu.setDescription(roleMenuVO.getDescription());
        boolean update = menu.updateById();
        return update?1:0;
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    public Integer deleteMenuById(Integer id) {
       // 删除自己的菜单
        boolean b = new Menu().deleteById(id);
        // 删除 t_role_menu中的菜单
        boolean delete = new RoleMenu().delete(new QueryWrapper<RoleMenu>().eq("menu_id", id));
        return b?1:0;
    }
}
