package com.java.demomp.ip.controller;


import com.java.demomp.ip.entity.Ip;
import com.java.demomp.ip.service.IpService;
import com.java.demomp.util.IpUtil2;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-20
 */
@RestController
@RequestMapping("/ip/ip")
public class IpController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    IpService ipService;

    @GetMapping("/{userId}")
    public Result getIp(@PathVariable Integer userId){
        String ipAddr = IpUtil2.getIpAddr(request);
        System.out.println("当前登录用户的ip为:"+ipAddr);
        ipService.collectIp(ipAddr,userId);
        return new Result(true, StatusCode.OK,"成功",ipAddr);
    }

    /**
     * 获得最近登录的ip
     * @return
     */
    @GetMapping
    public Result getRecentIp(){
      List<Ip> ipList =  ipService.getRecentIp();
      return new Result(true, StatusCode.OK,"成功",ipList);
    }
}
