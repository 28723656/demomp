package com.java.demomp.poker.service;

import com.java.demomp.admin.entity.User;
import com.java.demomp.poker.entity.PokerOnline;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-23
 */
public interface PokerOnlineService extends IService<PokerOnline> {

    List<User> getAllOnline();
}
