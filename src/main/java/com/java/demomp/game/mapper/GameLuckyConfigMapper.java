package com.java.demomp.game.mapper;

import com.java.demomp.game.VO.GameLuckyConfigVO;
import com.java.demomp.game.VO.TempForCardDictionaryVO;
import com.java.demomp.game.entity.GameLuckyConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-30
 */
public interface GameLuckyConfigMapper extends BaseMapper<GameLuckyConfig> {


    /**
     *
     * @param id
     * @param luckyId
     * @return
     */
    @Select("SELECT config.*, card. name AS name,card.top_star AS top_star " +
            "FROM t_game_lucky_config config LEFT JOIN t_game_card card on config.card_id = card.id AND card.deleted = 0 " +
            "WHERE config.round_id = #{id} AND config.lucky_id = #{luckyId} AND config.deleted = 0")
    List<GameLuckyConfigVO> getNewestLuckyConfig(@Param("id") Integer id, @Param("luckyId") Integer luckyId);

    /**
     * 查询每个卡片能在哪个卡包里面进行产出
     * @return
     */
    @Select("SELECT  " +
            "  config.card_id, lucky.`name` as luckyName  " +
            "FROM  " +
            "  t_game_lucky_config config  " +
            "LEFT JOIN t_game_lucky lucky ON config.lucky_id = lucky.id and lucky.deleted = 0  " +
            "WHERE  " +
            "  config.round_id = (  " +
            "    SELECT  " +
            "      r1.id  " +
            "    FROM  " +
            "      t_game_lucky_round r1  " +
            "    WHERE  " +
            "      r1.deleted = 0  " +
            "    AND r1.round_count = (  " +
            "      SELECT  " +
            "        max(r.round_count)  " +
            "      FROM  " +
            "        t_game_lucky_round r  " +
            "      WHERE  " +
            "        r.lucky_id = config.lucky_id  " +
            "      AND r.deleted = 0  " +
            "    )  " +
            "    AND r1.lucky_id = config.lucky_id  " +
            "  )  " +
            " and config.deleted = 0  " +
            " ORDER BY config.card_id , luckyName")
    List<TempForCardDictionaryVO> getEveryCardOutputPlace();
}
