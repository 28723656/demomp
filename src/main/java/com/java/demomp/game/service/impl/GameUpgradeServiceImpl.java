package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.game.VO.GameUpgradeVO;
import com.java.demomp.game.entity.GameUpgrade;
import com.java.demomp.game.mapper.GameUpgradeMapper;
import com.java.demomp.game.service.GameUpgradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
@Service
public class GameUpgradeServiceImpl extends ServiceImpl<GameUpgradeMapper, GameUpgrade> implements GameUpgradeService {

    /**
     * 更新一个卡片的升星系统
     * @param gameUpgradeVO
     */
    public void updateStar(GameUpgradeVO gameUpgradeVO) {
        // 每次更新的时候要先删除card_id为xxx的，然后再添加

        // 1.删除
        int deleteNums = baseMapper.delete(new UpdateWrapper<GameUpgrade>().eq("card_id", gameUpgradeVO.getId()));

        // 2.插入
        if(gameUpgradeVO.getStar1()!=null){
            commonInsert(gameUpgradeVO,1,gameUpgradeVO.getStar1());
        }
        if(gameUpgradeVO.getStar2()!=null){
            commonInsert(gameUpgradeVO,2,gameUpgradeVO.getStar2());
        }
        if(gameUpgradeVO.getStar3()!=null){
            commonInsert(gameUpgradeVO,3,gameUpgradeVO.getStar3());
        }
        if(gameUpgradeVO.getStar4()!=null){
            commonInsert(gameUpgradeVO,4,gameUpgradeVO.getStar4());
        }
        if(gameUpgradeVO.getStar5()!=null){
            commonInsert(gameUpgradeVO,5,gameUpgradeVO.getStar5());
        }
        if(gameUpgradeVO.getStar6()!=null){
            commonInsert(gameUpgradeVO,6,gameUpgradeVO.getStar6());
        }
        if(gameUpgradeVO.getStar7()!=null){
            commonInsert(gameUpgradeVO,7,gameUpgradeVO.getStar7());
        }
        if(gameUpgradeVO.getStar8()!=null){
            commonInsert(gameUpgradeVO,8,gameUpgradeVO.getStar8());
        }
        if(gameUpgradeVO.getStar9()!=null){
            commonInsert(gameUpgradeVO,9,gameUpgradeVO.getStar9());
        }
        if(gameUpgradeVO.getStar10()!=null){
            commonInsert(gameUpgradeVO,10,gameUpgradeVO.getStar10());
        }
    }

    /**
     * 公共的插入方法
     */
    public  void commonInsert(GameUpgradeVO gameUpgradeVO,Integer star,int num){
        GameUpgrade gameUpgrade = new GameUpgrade();
        gameUpgrade.setCardId(gameUpgradeVO.getId());
        gameUpgrade.setStar(star);
        gameUpgrade.setNum(num);
        baseMapper.insert(gameUpgrade);
    }
}
