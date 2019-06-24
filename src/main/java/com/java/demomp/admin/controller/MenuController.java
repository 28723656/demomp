package com.java.demomp.admin.controller;


import com.java.demomp.admin.service.MenuService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 获取列表   List<RoleMenuVO>
     * @return
     */
    @GetMapping
    public Result getMenuList(){
        return new Result(true, StatusCode.OK, "查询成功", menuService.getMenuList());
    }



}
