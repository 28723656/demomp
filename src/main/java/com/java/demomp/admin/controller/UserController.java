package com.java.demomp.admin.controller;


import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.service.UserService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
     * 通过角色找到用户
     * @return
     */
    @GetMapping("/user")
    public Result getUserByRole(){
        return new Result(true, StatusCode.OK, "查询成功", userService.getUserByRole());

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

    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse response){
       User resultUser = userService.login(user);
       if(resultUser != null){
           resultUser.setPassword(null);
           return new Result(true,StatusCode.OK,"登录成功",resultUser);
       }else {
           return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
       }

    }
}
