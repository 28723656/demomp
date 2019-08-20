package com.java.demomp.password.controller;


import com.java.demomp.password.entity.Password;
import com.java.demomp.password.service.PasswordService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-20
 */
@RestController
@RequestMapping("/password/password")
public class PasswordController {

    @Autowired
    PasswordService passwordService;

    /**
     * 加密密码
     * @param password
     * @return
     */
    @PostMapping("/{userId}")
    public Result addPassword(@RequestBody Password password,@PathVariable Integer userId){
        boolean b = passwordService.addPassword(password,userId);
        return new Result(true, StatusCode.OK,"添加成功");
    }


    /**
     * 展示数据
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result getInfo(@PathVariable Integer userId){
        List<Password> passwordList =  passwordService.getInfo(userId);
        return new Result(true, StatusCode.OK,"查询成功",passwordList);
    }


    /**
     * 解开密码
     * @param password
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public Result openPassword(@RequestBody Password password,@PathVariable Integer userId){
       Password resultPassword = passwordService.openPassword(password,userId);
       if(resultPassword!=null){
           return new Result(true,StatusCode.OK,"成功解开密码",resultPassword);
       }else {
           return new Result(false,StatusCode.ERROR,"无法解开密码");
       }
    }

    /**
     * 删除
     * @param password
     * @return
     */
    @DeleteMapping
    public Result deletePassword(@RequestBody Password password){
       Integer i =  passwordService.deletePasswordById(password.getId());
       if(i>0){
           return new Result(true,StatusCode.OK,"删除成功");
       }else {
           return new Result(false,StatusCode.ERROR,"删除失败");
       }
    }
}
