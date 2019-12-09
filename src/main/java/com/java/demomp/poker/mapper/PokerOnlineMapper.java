package com.java.demomp.poker.mapper;

import com.java.demomp.admin.entity.User;
import com.java.demomp.poker.entity.PokerOnline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-23
 */
public interface PokerOnlineMapper extends BaseMapper<PokerOnline> {

    /**
     * 查询3分钟内在线的人
     * @return
     */
    @Select("select u.id,u.nick_name from t_poker_online poker left join t_user u on u.id = poker.user_id and u.deleted = 0 where poker.deleted = 0 and poker.online_time >= DATE_ADD(NOW(),INTERVAL -5 minute)")
    List<User> getAllOnline();
}
