package com.java.demomp.admin.service.impl;

import com.java.demomp.admin.entity.Menu;
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
}
