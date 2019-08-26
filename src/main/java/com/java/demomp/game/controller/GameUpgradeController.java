package com.java.demomp.game.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.VO.GameUpgradeVO;
import com.java.demomp.game.entity.GameUpgrade;
import com.java.demomp.game.service.GameUpgradeService;
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
@RequestMapping("/game/upgrade")
public class GameUpgradeController {

    @Autowired
    GameUpgradeService gameUpgradeService;

    /**
     * 通过卡片id获得升星数据
     * @param cardId
     * @return
     */
    @GetMapping("/{cardId}")
    public Result listCard(@PathVariable Integer cardId){
        List<GameUpgrade> list = gameUpgradeService.list(new QueryWrapper<GameUpgrade>().eq("card_id", cardId));
        return new Result(true, StatusCode.OK,"查询成功",list);
    }


    @PutMapping
    public Result updateStar(@RequestBody GameUpgradeVO gameUpgradeVO){
        gameUpgradeService.updateStar(gameUpgradeVO);
        return new Result(true,StatusCode.OK,"更新成功");
    }
}
