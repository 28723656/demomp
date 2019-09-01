package com.java.demomp.game.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameMyMoney;
import com.java.demomp.game.service.GameMyMoneyService;
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
@RequestMapping("/game/myMoney")
public class GameMyMoneyController {


    @Autowired
    GameMyMoneyService gameMyMoneyService;

    /**
     * 通过userId获取用户的货币信息
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result getMyMoneyByUserId(@PathVariable Integer userId){
        return new Result(true, StatusCode.OK,"查询成功",gameMyMoneyService.list(new QueryWrapper<GameMyMoney>().eq("user_id",userId)));
    }
}
