package com.java.demomp.admin.controller;


import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.service.RoleService;
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
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public Result getRoleList(){
      return new Result(true, StatusCode.OK, "查询成功", roleService.getRoleList());
    }

    @GetMapping("/list")
    public Result getRoles(){
        return new Result(true, StatusCode.OK, "查询成功", roleService.getRoles());
    }


    /**
     * 添加角色
     * @param roleMenuVO
     * @return
     */
    @PostMapping
    public Result addRole(@RequestBody RoleMenuVO roleMenuVO){
        Integer addNum = roleService.addRole(roleMenuVO);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 更新角色
     * @param roleMenuVO
     * @return
     */
    @PutMapping
    public Result updateRole(@RequestBody RoleMenuVO roleMenuVO){
        Integer updateNum = roleService.updateRole(roleMenuVO);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteRole(@PathVariable Integer id){
        Integer deleteNum = roleService.deleteRole(id);
        if(deleteNum > 0){
            return new Result(true,StatusCode.OK,"删除成功");
        }else {
            return new Result(false,StatusCode.ERROR,"不能删除带有用户的角色");
        }
    }

}
