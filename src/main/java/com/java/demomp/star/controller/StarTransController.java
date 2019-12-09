package com.java.demomp.star.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.java.demomp.admin.service.UserRoleService;
import com.java.demomp.star.entity.StarTrans;
import com.java.demomp.star.service.StarTransService;
import com.java.demomp.star.vo.StarTransUserVO;
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
@RequestMapping("/star/trans")
public class StarTransController {
  
  @Autowired
  private StarTransService starTransService;
  
  /**
   * 通过用户id获取这个人的交易列表
   * @param userId
   * @return
   */
  @GetMapping("/{userId}")
  public Result getTransListByUserId(@PathVariable Integer userId){
    List<StarTransUserVO> list =  starTransService.getTransListByUserId(userId);
    return new Result(true, StatusCode.OK,"查询成功",list);
  }


  /**
   * 来做个交易
   * @param starTrans
   * @return
   */
  @PostMapping
  public Result doATrans(@RequestBody StarTrans starTrans){
    starTransService.doATrans(starTrans);
    return new Result(true,StatusCode.OK,"更新成功",starTrans);
  }

  /**
   * 获取可交易的人
   * @return
   */
  @GetMapping("/transUser")
  public Result getCanTransUser(){
    return new Result(true,StatusCode.OK,"查询成功",starTransService.getCanTransUser()); 
  }
  
}
