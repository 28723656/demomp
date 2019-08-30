package com.java.demomp.game.controller;


import com.java.demomp.game.VO.GameLuckyConfigVO;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.java.demomp.game.service.GameLuckyConfigService;
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
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/game/luckyConfig")
public class GameLuckyConfigController {

    @Autowired
    GameLuckyConfigService gameLuckyConfigService;

    /**
     * 批量添加数据
     * @param configList
     * @return
     */
    @PostMapping
    public Result addLuckyConfig(@RequestBody List<GameLuckyConfig> configList){
       boolean b = gameLuckyConfigService.addLuckyConfig(configList);
       if(b){
           return new Result(true, StatusCode.OK,"插入成功");
       }else {
           return new Result(false, StatusCode.ERROR,"插入失败");
       }
    }

    /**
     * 获取最新的一次的配置
     * @return
     */
    @GetMapping("/last/{luckyId}")
    public Result getNewestLuckyConfig(@PathVariable Integer luckyId){
      List<GameLuckyConfig> list =  gameLuckyConfigService.getNewestLuckyConfig(luckyId);
      if(list !=null){
          return new Result(true, StatusCode.OK,"查询成功",list);
      }else {
          return new Result(false, StatusCode.NODATA,"没有数据");
      }
    }
}
