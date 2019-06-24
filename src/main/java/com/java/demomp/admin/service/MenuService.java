package com.java.demomp.admin.service;

import com.java.demomp.admin.entity.Menu;
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
public interface MenuService extends IService<Menu> {

    List<Menu> getMenuList();
}
