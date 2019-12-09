package com.java.demomp.poker.service.impl;

import com.java.demomp.admin.entity.User;
import com.java.demomp.poker.entity.PokerOnline;
import com.java.demomp.poker.mapper.PokerOnlineMapper;
import com.java.demomp.poker.service.PokerOnlineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-23
 */
@Service
public class PokerOnlineServiceImpl extends ServiceImpl<PokerOnlineMapper, PokerOnline> implements PokerOnlineService {

    /**
     * 获取在线的人
     * @return
     */
    public List<User> getAllOnline() {
        return baseMapper.getAllOnline();
    }
}
