package com.java.demomp.game.mapper;

import com.java.demomp.game.VO.LuckyCountVO;
import com.java.demomp.game.VO.RecordRewardCardVO;
import com.java.demomp.game.entity.GameRecordReward;
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
 * @since 2019-08-31
 */
public interface GameRecordRewardMapper extends BaseMapper<GameRecordReward> {

    /**
     * 统计
     * @param idStr
     * @return
     */
    @Select("SELECT  " +
            "  reward.*, card.`name` AS cardName  " +
            "FROM  " +
            "  t_game_record_reward reward  " +
            "LEFT JOIN t_game_card card ON reward.card_id = card.id  " +
            "AND card.deleted = 0  " +
            "WHERE  " +
            "  reward.unique_mark = #{idStr}  " +
            "AND reward.deleted = 0")
    List<RecordRewardCardVO> getEntityAndCardName(@Param("idStr") String idStr);

    /**
     * 统计最近几个月来 每种卡片获得的数量总和
     * @param userId
     * @param monthCount
     * @return
     */
    @Select("SELECT  " +
            "  IFNULL(reward.card_type,NULL) AS cardType,  " +
            "  IFNULL(sum(reward.card_nums), 0) AS cardNum  " +
            "FROM  " +
            "  t_game_record_reward reward  " +
            "WHERE  " +
            "  reward.user_id = #{userId}  " +
            "AND DATE_SUB(CURDATE(), INTERVAL #{monthCount} MONTH) <= CURDATE()  " +
            "AND deleted = 0  " +
            "GROUP BY  " +
            "  reward.card_type" +
            " ORDER BY cardType asc")
    List<LuckyCountVO> statisticsCardType(@Param("userId")Integer userId, @Param("monthCount")Integer monthCount);

    /**
     * 统计最近几个月来 每种卡片获得的数量总和
     * @param userId
     * @param monthCount
     * @return
     */
    @Select("SELECT  " +
            "  reward.reward_type AS rewardType,  " +
            "  IFNULL(  " +
            "    sum(reward.reward_coin_nums),  " +
            "    0  " +
            "  ) AS rewardNum,  " +
            "  IFNULL(sum(reward.card_nums), 0) AS cardNum  " +
            "FROM  " +
            "  t_game_record_reward reward  " +
            "WHERE  " +
            "  reward.user_id = #{userId}  " +
            "AND DATE_SUB(CURDATE(), INTERVAL #{monthCount} MONTH) <= CURDATE()  " +
            "AND deleted = 0  " +
            "GROUP BY  " +
            "  reward.reward_type  " +
            "ORDER BY  " +
            "  rewardType ASC")
    List<LuckyCountVO> statisticsCardAndCoin(@Param("userId")Integer userId, @Param("monthCount")Integer monthCount);

    /**
     * 统计用户最近 count条记录
     * @param userId
     * @param count
     * @return
     */
    @Select("SELECT  " +
            "  card. NAME AS cardName,  " +
            "  card.type AS cardType,  " +
            "  reward.reward_type AS rewardType,  " +
            "  reward.card_nums AS cardNum,  " +
            "  reward.reward_coin_nums AS coinNum,  " +
            "  reward.create_time AS rewardTime,  " +
            "   reward.unique_mark AS uniqueMark  " +
            "FROM  " +
            "  t_game_record_reward reward  " +
            "LEFT JOIN t_game_card card ON reward.card_id = card.id  " +
            "AND card.deleted = 0  " +
            "WHERE  " +
            "  reward.user_id = #{userId}  " +
            "AND DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= CURDATE()  " +
            "AND reward.deleted = 0  " +
            "ORDER BY  " +
            "  reward.create_time DESC,  " +
            "  reward.unique_mark  " +
            "LIMIT #{count}")
    List<LuckyCountVO> statisticsLatestRecord(@Param("userId")Integer userId, @Param("count")Integer count);
}
