package com.java.demomp.game.mapper;

import com.java.demomp.game.entity.GameCost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
public interface GameCostMapper extends BaseMapper<GameCost> {

    /**
     * 获得满级卡片的属性
     * @return
     */
    @Select("SELECT  " +
            "  cost2.card_id,  " +
            "  cost2.inc_coin,  " +
            "  cost2.inc_experience,  " +
            "  cost2.low_percent,  " +
            "  cost2.top_percent  " +
            "FROM  " +
            "  t_game_cost cost2  " +
            "WHERE  " +
            "  id IN (  " +
            "    SELECT  " +
            "      max(cost.id) AS maxId  " +
            "    FROM  " +
            "      t_game_card card  " +
            "    LEFT JOIN t_game_cost cost ON cost.card_id = card.id  " +
            "    AND cost.deleted = 0  " +
            "    AND card.deleted = 0  " +
            "    WHERE  " +
            "      card.deleted = 0  " +
            "    GROUP BY  " +
            "      cost.card_id  " +
            "  )")
    List<GameCost> getMaxList();
}
