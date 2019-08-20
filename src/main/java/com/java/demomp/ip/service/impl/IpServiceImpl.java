package com.java.demomp.ip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.admin.service.UserService;
import com.java.demomp.ip.entity.Ip;
import com.java.demomp.ip.mapper.IpMapper;
import com.java.demomp.ip.service.IpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-20
 */
@Service
public class IpServiceImpl extends ServiceImpl<IpMapper, Ip> implements IpService {

    @Autowired
    UserService userService;

    public void collectIp(String ipAddr,Integer userId) {
        Ip ip = new Ip();
        ip.setIp(ipAddr);
        ip.setLoginTime(LocalDateTime.now());
        ip.setUserId(userId);
        ip.setUsername(userService.getById(ip.getUserId()).getNickName());
        baseMapper.insert(ip);
    }

    /**
     * 获得最近登录的50个ip
     * @return
     */
    public List<Ip> getRecentIp() {
     return   baseMapper.selectList(new QueryWrapper<Ip>().orderByDesc("create_time").last("limit 50"));
    }
}
