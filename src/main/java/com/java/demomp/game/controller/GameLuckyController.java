package com.java.demomp.game.controller;


import com.java.demomp.game.entity.GameLucky;
import com.java.demomp.game.service.GameLuckyService;
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
 * @since 2019-08-29
 */
@RestController
@RequestMapping("/game/lucky")
public class GameLuckyController {

    @Autowired
    GameLuckyService gameLuckyService;

    /**
     * 获取list表
     * @return
     */
    @GetMapping
    public Result list(){
        return new Result(true, StatusCode.OK,"查询成功",gameLuckyService.list());
    }

    /**
     * 修改
     * @param gameLucky
     * @return
     */
    @PutMapping
    public Result updateLucky(@RequestBody GameLucky gameLucky) {
        return new Result(true, StatusCode.OK,"修改成功",gameLuckyService.updateById(gameLucky));
    }

    /**
     * 添加
     * @param gameLucky
     * @return
     */
    @PostMapping
    public Result addLucky(@RequestBody GameLucky gameLucky){
         return new Result(true, StatusCode.OK,"修改成功",gameLuckyService.save(gameLucky));
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public  Result deleteLucky(@PathVariable Integer id){
       boolean b =  gameLuckyService.deleteLucky(id);
       if(b){
           return new Result(true,StatusCode.OK,"删除成功");
       }else {
           return new Result(false,StatusCode.ERROR,"删除失败");
       }
    }



    // `/game/lucky/${user.id}/${luckyId}/${openTimes}`)

    /**
     * 重点   开箱部分
     * @return
     */
    @PostMapping("/{userId}/{luckyId}/{openTimes}")
    public Result openLucky(@PathVariable("userId") Integer userId,@PathVariable("luckyId") Integer luckyId,@PathVariable("openTimes") Integer openTimes){
       int code =  gameLuckyService.openLucky(userId,luckyId,openTimes);
       if(code == StatusCode.OK){
           return new Result(true,StatusCode.OK,"开箱吧，少年");
       }else if(code == StatusCode.NOMONEY){
           return new Result(false,StatusCode.ERROR,"金币不足");
       }else {
           return new Result(false,StatusCode.ERROR,"数据异常");
       }

    }
}
