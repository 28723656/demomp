package com.java.demomp.star.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.admin.entity.User;
import com.java.demomp.star.entity.StarTrans;
import com.java.demomp.star.entity.StarUser;
import com.java.demomp.star.mapper.StarTransMapper;
import com.java.demomp.star.service.StarTransService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.star.service.StarUserService;
import com.java.demomp.star.vo.StarTransUserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-12-09
 */
@Service
public class StarTransServiceImpl extends ServiceImpl<StarTransMapper, StarTrans> implements StarTransService {

  
  @Autowired
  private StarUserService starUserService;
  
  public List<StarTransUserVO> getTransListByUserId(Integer userId) {
    return baseMapper.getTransListByUserId(userId);
  }

  @Transactional
  public void doATrans(StarTrans starTrans) {
    StarUser starUser = starUserService.getOne(new QueryWrapper<StarUser>().eq("user_id", starTrans.getUserId()));
    StarUser otherUser = starUserService.getOne(new QueryWrapper<StarUser>().eq("user_id", starTrans.getOtherId()));
    if(starUser ==null){
      starUser = new StarUser();
      starUser.setUserId(starTrans.getUserId());
      starUser.setNum(0);
      starUser.setType(3);
      starUser.insert();
    }
    if(otherUser ==null){
      otherUser = new StarUser();
      otherUser.setUserId(starTrans.getOtherId());
      otherUser.setNum(0);
      otherUser.setType(3);
      otherUser.insert();
    }
    starUser.setNum(starUser.getNum() - starTrans.getTransNum());
    otherUser.setNum(otherUser.getNum() + starTrans.getTransNum());
    // 1.用户减分
    starUserService.updateById(starUser);
    // 2.其他用户加分
    starUserService.updateById(otherUser);
    // 3.记录账单
    starTrans.setTransToType(1);
    starTrans.setTransToType(1);
    baseMapper.insert(starTrans);
  }

  public List<User> getCanTransUser() {
    return baseMapper.getCanTransUser();
  }
}
