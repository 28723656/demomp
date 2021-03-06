package com.java.demomp.admin.controller;


import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.service.UserService;
import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.service.PlanService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    PlanService planService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HttpServletRequest request;



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
        if(addNum == 1){
            return new Result(true,StatusCode.OK,"添加成功");
        } else if(addNum == 0) {
            return  new Result(false,StatusCode.ERROR,"添加失败");
        }else {
            return  new Result(false,StatusCode.ERROR,"手机号码被占用");
        }

    }


    /**
     * 修改用户
     * @param userRoleVO
     * @return
     */
    @PutMapping
    public Result updateUser(@RequestBody UserRoleVO userRoleVO){
        Integer addNum = userService.updateUser(userRoleVO);
        if(addNum == 1){
            return new Result(true,StatusCode.OK,"修改成功");
        }else {
            return  new Result(false,StatusCode.ERROR,"修改失败");
        }

    }


    /**
     * 删除用户
     * @param
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteUserById(@PathVariable Integer id){
        Integer deleteNum = userService.deleteUserById(id);
        if(deleteNum == 1){
            return new Result(true,StatusCode.OK,"删除成功");
        }else if(deleteNum == 0){
            return new Result(false,StatusCode.ERROR,"删除失败");
        }else {
            return new Result(false,StatusCode.ERROR,"管理员是你想删就能删的？");
        }

    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
       User resultUser = userService.login(user);
       if(resultUser != null){
           // 登录的时候把用户信息，和通用计划放入localStorage里面
           List<Plan> basePlanList = planService.getBasePlanByUserId(resultUser.getId());
           resultUser.setPassword(null);
           Map<String,Object> map = new HashMap<>();
           map.put("basePlanList",basePlanList);
           map.put("user",resultUser);
           return new Result(true,StatusCode.OK,"登录成功",map);
       }else {
           return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
       }

    }

    /**
     * 通过用户id获取菜单权限
     * @param id
     * @return
     */
    @GetMapping("/menu/{id}")
    public Result getMenusByUserId(@PathVariable Integer id){
       List<String> userMenus =  userService.getMenusByUserId(id);
       if(userMenus!=null && userMenus.size() > 0){
           return new Result(true,StatusCode.OK,"查询成功",userMenus);
       }else {
           return new Result(false,StatusCode.ERROR,"没有权限");
       }

    }
}
