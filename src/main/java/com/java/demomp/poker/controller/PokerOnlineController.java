package com.java.demomp.poker.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.admin.entity.User;
import com.java.demomp.poker.entity.PokerOnline;
import com.java.demomp.poker.service.PokerOnlineService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/poker/online")
public class PokerOnlineController {

    @Autowired
    PokerOnlineService pokerOnlineService;

    /**
     * 把用户标记为在线
     * @param userId
     * @return
     */
    @PostMapping("/{userId}")
    public Result markAsOnline(@PathVariable Integer userId) {
        // 如果有userId,就更新，没有就插入
        PokerOnline pokerOnline = pokerOnlineService.getBaseMapper().selectOne(new QueryWrapper<PokerOnline>().eq("user_id", userId));
        if(pokerOnline == null){
            PokerOnline insertPokerOnline = new PokerOnline();
            insertPokerOnline.setUserId(userId);
            insertPokerOnline.setOnlineTime(LocalDateTime.now());
            pokerOnlineService.save(insertPokerOnline);
        }else {
            pokerOnline.setOnlineTime(LocalDateTime.now());
            pokerOnlineService.updateById(pokerOnline);
        }
        return new Result(true, StatusCode.OK,"onLine成功");
    }


    /**
     * 获取3分钟内在线的人
     * @return
     */
    @GetMapping
    public Result getAllOnline(){
      List<User> userList =  pokerOnlineService.getAllOnline();
      if(userList.size()>0){
          return new Result(true,StatusCode.OK,"查询成功",userList);
      }else {
          return new Result(false,StatusCode.OK,"没有用户");
      }
    }




















}
