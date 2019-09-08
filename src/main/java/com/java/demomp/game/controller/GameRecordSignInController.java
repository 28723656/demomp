package com.java.demomp.game.controller;


import com.java.demomp.game.VO.RecordSignInVO;
import com.java.demomp.game.entity.GameSignIn;
import com.java.demomp.game.service.GameRecordSignInService;
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
 * @since 2019-09-08
 */
@RestController
@RequestMapping("/game/recordSignIn")
public class GameRecordSignInController {

    @Autowired
    GameRecordSignInService gameRecordSignInService;
    /**
     * 用户获得当日签到奖励
     * @param userId
     * @return
     */
    @PutMapping("/user/{userId}")
    public Result getSigInReward(@PathVariable Integer userId) {
      GameSignIn gameSignIn =  gameRecordSignInService.getSigInReward(userId);
      if(gameSignIn == null){
          return new Result(false, StatusCode.ERROR,"已经获得，或是没有数据");
      }else {
          return new Result(true,StatusCode.OK,"获得签到奖励",gameSignIn);
      }
    }


    /**
     * 获取全部用户几天内登陆情况
     * @param day
     * @return
     */
    @GetMapping("/record/{day}")
    public Result getDaysRecord(@PathVariable Integer day){
       List<RecordSignInVO> list  = gameRecordSignInService.getDaysRecord(day);
       return new Result(true,StatusCode.OK,"查询成功",list);
    }


}
