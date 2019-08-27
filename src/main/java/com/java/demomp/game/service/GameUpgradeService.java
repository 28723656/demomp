package com.java.demomp.game.service;

import com.java.demomp.game.VO.GameUpgradeVO;
import com.java.demomp.game.entity.GameUpgrade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
public interface GameUpgradeService extends IService<GameUpgrade> {

    void updateStar(GameUpgradeVO gameUpgradeVO);

    int deleteCardByCardId(Integer cardId);
}
