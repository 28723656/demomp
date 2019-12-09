package com.java.demomp.star.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.star.entity.StarUser;
import com.java.demomp.star.service.StarUserService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-12-09
 */
@RestController
@RequestMapping("/star/user")
public class StarUserController {
  
  @Autowired
  StarUserService starUserService;

  /**
   * 通过用户id获取用户的分数
   * @param userId
   * @return
   */
  @GetMapping("/{userId}")
  public Result getMyStarNum(@PathVariable Integer userId){
    StarUser starUser = starUserService.getOne(new QueryWrapper<StarUser>().eq("user_id", userId));
    return new Result(true, StatusCode.OK,"查询成功",starUser);
  }

  /**
   * 查询所有的列表
   * @return
   */
  @GetMapping
  public Result getStarUserList(){
    return new Result(true,StatusCode.OK,"查询成功",starUserService.list());
  }
  
  
  
}
