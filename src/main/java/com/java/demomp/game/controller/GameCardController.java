package com.java.demomp.game.controller;


import com.java.demomp.game.entity.GameCard;
import com.java.demomp.game.service.GameCardService;
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
 * @since 2019-08-26
 */
@RestController
@RequestMapping("/game/card")
public class GameCardController {


    @Autowired
    GameCardService gameCardService;

    /**
     * 获取卡片列表
     * @return
     */
    @GetMapping
    public Result list(){
        System.out.println(gameCardService.list());
        return new Result(true, StatusCode.OK,"查询成功",gameCardService.list());
    }

    /**
     * 更新卡片
     * @param gameCard
     * @return
     */
    @PutMapping
    public Result update(@RequestBody GameCard gameCard ){
       return new Result(true,StatusCode.OK,"更新成功", gameCardService.updateById(gameCard));
    }

    /**
     * 添加
     * @param gameCard
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody GameCard gameCard){
        return new Result(true,StatusCode.OK,"添加成功",gameCardService.save(gameCard));
    }
}
