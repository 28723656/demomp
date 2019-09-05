package com.java.demomp.game.controller;


import com.java.demomp.game.entity.GameCost;
import com.java.demomp.game.service.GameMyCardService;
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
 * @since 2019-08-31
 */
@RestController
@RequestMapping("/game/myCard")
public class GameMyCardController {

    @Autowired
    GameMyCardService gameMyCardService;

    /**
     * 通过cardId升级卡片Id  传入3个参数 1用户id 2卡片id 3-升级类型（1-升级 2-升星）
     * @param cardId
     * @return
     */
    @PutMapping("/{userId}/{cardId}/{updateType}")
    public Result updateCardStar(@PathVariable Integer userId,@PathVariable Integer cardId,@PathVariable Integer updateType){
       boolean b = gameMyCardService.updateCardStar(userId,cardId,updateType);
       if(b){
           return new Result(true, StatusCode.OK,"升级成功");
       }else {
           return new Result(false,StatusCode.ERROR,"升级失败");
       }
    }


    /**
     * 通过用户id获取用户所有卡片的技能加成 1,2,3总类型
     * @param userId
     * @return
     */
    @GetMapping("/skillCount/{userId}")
    public Result getSkillTotal(@PathVariable Integer userId){
        GameCost gameCost = gameMyCardService.getSkillTotal(userId);
        return new Result(true,StatusCode.OK,"查询成功",gameCost);
    }
}
