package com.java.demomp.game.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameMyMoney;
import com.java.demomp.game.entity.GameRewardDay;
import com.java.demomp.game.service.GameMyMoneyService;
import com.java.demomp.game.service.GameRewardDayService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/game/rewardDay")
public class GameRewardDayController {

    @Autowired
    GameRewardDayService gameRewardDayService;

    @Autowired
    GameMyMoneyService myMoneyService;

    @PutMapping("/{userId}")
    public Result getRewardDay(@PathVariable Integer userId){
        GameRewardDay gameRewardDay = gameRewardDayService.getRewardDay(userId);
        // 能查到数据，说明用户能够领取奖励
        if(gameRewardDay !=null){
            // 已经领取过了
            if(gameRewardDay.getRewarded() == 1){
                return new Result(false, StatusCode.REPERROR,"已经领取过了");
            }else {
                // 领取奖励，标记为1
                gameRewardDay.setRewarded(1);
                gameRewardDay.setRewardTime(LocalDateTime.now());
                boolean b = gameRewardDayService.updateById(gameRewardDay);
                // 把获得的奖励 存放到我的金币库
                GameMyMoney gameMyMoney = myMoneyService.getOne(new QueryWrapper<GameMyMoney>().eq("user_id", userId).eq("type", 3));
                gameMyMoney.setMoneyNum(gameMyMoney.getMoneyNum()+gameRewardDay.getRewardCount());
                boolean b1 = gameMyMoney.updateById();
                return new Result(true,StatusCode.OK,"用户今天登陆了，领取了奖励",gameRewardDay);
            }
        }else {
            return  new Result(false, StatusCode.REPERROR,"用户没有技能");
        }

    }

}
