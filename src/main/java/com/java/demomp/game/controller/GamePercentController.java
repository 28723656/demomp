package com.java.demomp.game.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.game.entity.GamePercent;
import com.java.demomp.game.service.GamePercentService;
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
 * @since 2019-09-01
 */
@RestController
@RequestMapping("/game/percent")
public class GamePercentController {

    @Autowired
    GamePercentService gamePercentService;

    /**
     * 保存一个卡片出货数量的概率配置
     * @param list
     * @return
     */
    @PostMapping
    public Result addPercentConfig(@RequestBody List<GamePercent> list){
        // 老样子，先删除，后保存
        int cardId = list.get(0).getCardId();
        gamePercentService.remove(new UpdateWrapper<GamePercent>().eq("card_id", cardId));
        boolean saved = gamePercentService.saveBatch(list);
        if(saved){
            return new Result(true, StatusCode.OK,"添加成功");
        }else {
            return new Result(false,StatusCode.ERROR,"添加失败");
        }

    }

    /**
     * 通过cardId获取概率配置
     * @param cardId
     * @return
     */
    @GetMapping("/{cardId}")
    public Result getPercentConfigByCardId(@PathVariable Integer cardId){
        List<GamePercent> list = gamePercentService.list(new QueryWrapper<GamePercent>().eq("card_id", cardId));
        if(list.size()>0){
            return new Result(true,StatusCode.OK,"查询成功",list);
        }else {
            return new Result(false,StatusCode.NODATA,"查询成功");
        }
    }
}
