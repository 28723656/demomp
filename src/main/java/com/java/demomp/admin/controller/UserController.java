package com.java.demomp.admin.controller;


import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.service.UserService;
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
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取列表
     * @return
     */
    @GetMapping
    public Result getUserList(){
      return new Result(true, StatusCode.OK,"查询成功", userService.getUserList()) ;
    }

    /**
     * 添加用户
     * @param userRoleVO
     * @return
     */
    @PostMapping
    public Result addUser(@RequestBody UserRoleVO userRoleVO){
        Integer addNum = userService.addUser(userRoleVO);
        return new Result(true,StatusCode.OK,"添加成功");
    }



    /**
     * 修改用户
     * @param userRoleVO
     * @return
     */
    @PutMapping
    public Result updateUser(@RequestBody UserRoleVO userRoleVO){
        Integer addNum = userService.updateUser(userRoleVO);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /**
     * 删除用户
     * @param
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteUserById(@PathVariable Integer id){
        Integer deleteNum = userService.deleteUserById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
