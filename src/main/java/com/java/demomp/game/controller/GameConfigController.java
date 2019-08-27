package com.java.demomp.game.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.entity.GameConfig;
import com.java.demomp.game.service.GameConfigService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-27
 */
@RestController
@RequestMapping("/game/config")
public class GameConfigController {

    @Autowired
    GameConfigService gameConfigService;

    /**
     * 通过cardId查询配置信息
     * @param cardId
     * @return
     */
    @GetMapping("/{cardId}")
    public Result getConfigByCardId(@PathVariable Integer cardId){
        GameConfig gameConfig = gameConfigService.getOne(new QueryWrapper<GameConfig>().eq("card_id", cardId));
        if(gameConfig!=null){
            return new Result(true, StatusCode.OK,"查询成功",gameConfig);
        }else {
            return new Result(false,StatusCode.ERROR,"没有数据");
        }
    }

    /**
     * 保存配置信息
     * @param gameConfig
     * @return
     */
    @PostMapping
    public Result addConfig(@RequestBody GameConfig gameConfig){
      boolean b =  gameConfigService.saveConfig(gameConfig);
      if(b){
          return new Result(true,StatusCode.OK,"保存成功");
      }else {
          return new Result(false,StatusCode.ERROR,"保存失败");
      }
    }
}
