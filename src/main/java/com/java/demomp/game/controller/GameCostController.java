package com.java.demomp.game.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameCost;
import com.java.demomp.game.service.GameCostService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
@RestController
@RequestMapping("/game/cost")
public class GameCostController {

    @Autowired
    GameCostService gameCostService;

    /**
     * 批量保存
     * @param list
     * @return
     */
    @PostMapping
    public Result addList(@RequestBody List<GameCost> list){
        // 先删除原有的记录
        gameCostService.remove(new QueryWrapper<GameCost>().eq("card_id",list.get(0).getCardId()));
        boolean b = gameCostService.saveBatch(list);
        if(b){
            return new Result(true, StatusCode.OK,"添加成功");
        }else {
            return new Result(false, StatusCode.ERROR,"添加失败");
        }
    }
}
