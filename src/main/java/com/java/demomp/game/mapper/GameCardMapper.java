package com.java.demomp.game.mapper;

import com.java.demomp.game.VO.GameMyCardVO;
import com.java.demomp.game.VO.TempForCardDictionaryVO;
import com.java.demomp.game.entity.GameCard;
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
 * @since 2019-08-26
 */
public interface GameCardMapper extends BaseMapper<GameCard> {

    // 优化版的sql
    @Select("SELECT  " +
            "  myCard.num AS cardNum,  " +
            "  myCard.card_id AS cardId,  " +
            "  myCard.current_star AS currentStar,  " +
            "  myCard.current_rank AS currentRank,  " +
            "  card.skill,  " +
            "  card.type AS cardType,  " +
            "  card.top_star AS topStar,  " +
            "  card. NAME AS cardName,  " +
            "  up.num AS updateStarNeedNum,  " +
            "  cost1.inc_coin AS incCoin,  " +
            "  cost1.inc_experience AS incExperience,  " +
            "  cost1.low_percent AS incKeyLow,  " +
            "  cost1.top_percent AS incKeyTop,  " +
            "  cost2.cost,  " +
            "  cost2.inc_coin AS incCoinNext,  " +
            "  cost2.inc_experience AS incExperienceNext,  " +
            "  cost2.low_percent AS incKeyLowNext,  " +
            "  cost2.top_percent AS incKeyTopNext,  " +
            "  max(cost3.rank) AS starTopRank,  " +
            "  max(cost4.rank) AS topRank," +
            " cost4.inc_coin AS incCoinFull, " +
            " cost4.inc_experience AS incExperienceFull, " +
            " cost4.low_percent AS incKeyLowFull, " +
            " cost4.top_percent AS incKeyTopFull  " +
            "FROM  " +
            "  t_game_my_card myCard  " +
            "LEFT JOIN t_game_card card ON card.id = myCard.card_id  " +
            "AND card.deleted = 0  " +
            "LEFT JOIN t_game_upgrade up ON up.card_id = myCard.card_id  " +
            "AND up.star = myCard.current_star + 1  " +
            "AND up.deleted = 0  " +
            "LEFT JOIN t_game_cost cost1 ON cost1.card_id = myCard.card_id  " +
            "AND cost1.rank = myCard.current_rank  " +
            "AND cost1.deleted = 0  " +
            "LEFT JOIN t_game_cost cost2 ON cost2.card_id = myCard.card_id  " +
            "AND cost2.rank = myCard.current_rank + 1  " +
            "AND cost2.deleted = 0  " +
            "LEFT JOIN t_game_cost cost3 ON cost3.card_id = myCard.card_id  " +
            "AND cost3.star = myCard.current_star  " +
            "LEFT JOIN t_game_cost cost4 ON cost4.card_id = myCard.card_id  " +
            "AND cost4.star = card.top_star  " +
            "WHERE  " +
            "  myCard.user_id = #{userId}  " +
            "GROUP BY  " +
            "  myCard.card_id  " +
            "ORDER BY  " +
     /*       "  myCard.current_rank DESC,  " +
            "  myCard.current_star DESC,  " +*/
            "  card. NAME ASC")
    List<GameMyCardVO> showMyCard(@Param("userId") Integer userId);

    /**
     * 选出每个星级可以升级的等级和消耗多少张卡片
     * @return
     */
    @Select("SELECT  " +
            "  up.card_id AS cardId,  " +
            "  up.num AS starNum,  " +
            "  up.star,  " +
            "  count(cost.rank) AS rankNum  " +
            "FROM  " +
            "  t_game_upgrade up  " +
            "LEFT JOIN t_game_card card ON up.card_id = card.id  " +
            "AND card.deleted = 0  " +
            "LEFT JOIN t_game_cost cost ON up.card_id = cost.card_id  " +
            "AND up.star = cost.star  " +
            "WHERE  " +
            "  up.deleted = 0  " +
            "GROUP BY  " +
            "  up.id  " +
            "ORDER BY  " +
            "  card.type_dict ASC,  " +
            "  card. NAME ASC,  " +
            "  up.star ASC")
    List<TempForCardDictionaryVO> selectEveryStarDetail();
}
