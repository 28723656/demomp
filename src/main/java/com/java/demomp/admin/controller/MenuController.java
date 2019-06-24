package com.java.demomp.admin.controller;


import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.service.MenuService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加菜单
     * @param roleMenuVO
     * @return
     */
    @PostMapping
    public Result addMenu(@RequestBody RoleMenuVO roleMenuVO){
        Integer addNum = menuService.addMenu(roleMenuVO);
        return new Result(true,StatusCode.OK,"添加成功");
    }



    /**
     * 修改菜单
     * @param roleMenuVO
     * @return
     */
    @PutMapping
    public Result updateMenu(@RequestBody RoleMenuVO roleMenuVO){
        Integer addNum = menuService.updateMenu(roleMenuVO);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /**
     * 删除菜单
     * @param
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteMenuById(@PathVariable Integer id){
        Integer deleteNum = menuService.deleteMenuById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


}
