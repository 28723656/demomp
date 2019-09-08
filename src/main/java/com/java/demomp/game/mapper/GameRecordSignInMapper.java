package com.java.demomp.game.mapper;

import com.java.demomp.game.VO.RecordSignInVO;
import com.java.demomp.game.entity.GameRecordSignIn;
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
 * @since 2019-09-08
 */
public interface GameRecordSignInMapper extends BaseMapper<GameRecordSignIn> {

    @Select("SELECT * FROM t_game_record_sign_in WHERE user_id = #{userId} AND TO_DAYS(create_time) = TO_DAYS(NOW()) and deleted = 0")
    GameRecordSignIn checkSignIn(@Param("userId") Integer userId);

    /**
     * 获取全部用户几天内登陆情况
     * @param day
     * @return
     */
    @Select("SELECT record.*,u.nick_name FROM t_game_record_sign_in record left join t_user u on u.id = record.user_id and u.deleted = 0  " +
            "WHERE  record.deleted = 0 AND DATE_SUB(CURDATE(), INTERVAL #{day} DAY) <= date(record.create_time) order by record.create_time desc")
    List<RecordSignInVO> getDaysRecord(@Param("day") Integer day);
}
