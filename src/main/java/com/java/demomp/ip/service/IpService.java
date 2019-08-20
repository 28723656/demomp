package com.java.demomp.ip.service;

import com.java.demomp.ip.entity.Ip;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-20
 */
public interface IpService extends IService<Ip> {

    void collectIp(String ipAddr,Integer userId);

    List<Ip> getRecentIp();
}
