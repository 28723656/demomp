package com.java.demomp.game.controller;


import com.java.demomp.game.service.GameRecordRewardService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-31
 */
@RestController
@RequestMapping("/game/recordReward")
public class GameRecordRewardController {

    @Autowired
    GameRecordRewardService gameRecordRewardService;

    /**
     * 统计最近一个月用户获得了各种类型的卡片的数量
     * monthCount =>  统计几个月的
     * @param userId monthCount
     * @return
     */
    @GetMapping("/statisticsCard/{userId}/{monthCount}")
    public Result statisticsCardType(@PathVariable Integer userId,@PathVariable Integer monthCount){
        return new Result(true, StatusCode.OK,"查询成功",gameRecordRewardService.statisticsCardType(userId,monthCount));
    }

    /**
     * 统计最近一个月用户获得了各种类型的货币和卡片
     * monthCount =>  统计几个月的
     * @param userId monthCount
     * @return
     */
    @GetMapping("/statisticsCoin/{userId}/{monthCount}")
    public Result statisticsCardAndCoin(@PathVariable Integer userId,@PathVariable Integer monthCount){
        return new Result(true, StatusCode.OK,"查询成功",gameRecordRewardService.statisticsCardAndCoin(userId,monthCount));
    }

    /**
     * 统计用户最新count条数据
     * @param userId
     * @param count
     * @return
     */
    @GetMapping("/statisticsLatestRecord/{userId}/{count}")
    public Result statisticsLatestRecord(@PathVariable Integer userId,@PathVariable Integer count){
        return new Result(true, StatusCode.OK,"查询成功",gameRecordRewardService.statisticsLatestRecord(userId,count));
    }



}
