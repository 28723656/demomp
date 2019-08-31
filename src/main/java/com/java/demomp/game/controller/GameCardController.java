package com.java.demomp.game.controller;


import com.java.demomp.game.entity.GameCard;
import com.java.demomp.game.entity.GamePercent;
import com.java.demomp.game.service.GameCardService;
import com.java.demomp.game.service.GamePercentService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        gameCardService.saveCard(gameCard);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /**
     * 删除一个卡片，就是要删除很多很多关联的东西
     * @param cardId
     * @return
     */
    @DeleteMapping("/{cardId}")
    public Result delete(@PathVariable Integer cardId){
        int code = gameCardService.deleteCardByCardId(cardId);
        if(StatusCode.OK == code){
            return new Result(true,StatusCode.OK,"删除成功");
        }else if(StatusCode.ERROR == code) {
            return new Result(false,StatusCode.ERROR,"删除失败");
        }else {
            return new Result(false,StatusCode.CANNOTDELETE,"不能被删除");
        }
    }
}
