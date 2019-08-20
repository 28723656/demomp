package com.java.demomp.ip.service.impl;

import com.java.demomp.ip.entity.Ip;
import com.java.demomp.ip.mapper.IpMapper;
import com.java.demomp.ip.service.IpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public void collectIp(String ipAddr,Integer userId) {
        Ip ip = new Ip();
        ip.setIp(ipAddr);
        ip.setLoginTime(LocalDateTime.now());
        ip.setUserId(userId);
        baseMapper.insert(ip);
    }
}
