package com.java.demomp.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.entity.Menu;
import com.java.demomp.admin.entity.RoleMenu;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.mapper.MenuMapper;
import com.java.demomp.admin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    HttpSession session;

    @Autowired
    RedisTemplate redisTemplate;

    // redis存储的最大时间
    static final Integer REDIS_MAX_TIME = 7;

    public List<Menu> getMenuList() {
        if (redisTemplate.opsForValue().get("menuList") == null) {
            List<Menu> menuList = baseMapper.selectList(null);
            redisTemplate.opsForValue().set("menuList",menuList,REDIS_MAX_TIME, TimeUnit.DAYS);
            return menuList;
        }else {
            return (List<Menu>) redisTemplate.opsForValue().get("menuList");
        }
    }

    /**
     * 添加菜单
     *
     * @param roleMenuVO
     * @return
     */
    public Integer addMenu(RoleMenuVO roleMenuVO) {
        Menu menu = new Menu();
        menu.setName(roleMenuVO.getName());
        menu.setDescription(roleMenuVO.getDescription());
        boolean b = menu.insert();
        if(b){
            redisTemplate.delete("menuList");
            redisTemplate.delete("getRoleByMenuList");
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 更新菜单
     *
     * @param roleMenuVO
     * @return
     */
    public Integer updateMenu(RoleMenuVO roleMenuVO) {
        Menu menu = new Menu();
        menu.setId(roleMenuVO.getId());
        menu.setName(roleMenuVO.getName());
        menu.setDescription(roleMenuVO.getDescription());
        boolean b = menu.updateById();
        if(b){
            redisTemplate.delete("menuList");
            redisTemplate.delete("getRoleByMenuList");
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    public Integer deleteMenuById(Integer id) {
        // 删除自己的菜单
        boolean b = new Menu().deleteById(id);
        // 删除 t_role_menu中的菜单
        boolean delete = new RoleMenu().delete(new QueryWrapper<RoleMenu>().eq("menu_id", id));
        if(b && delete){
            redisTemplate.delete("menuList");
            redisTemplate.delete("getRoleByMenuList");
            return 1;
        }else {
            return 0;
        }
    }


}
