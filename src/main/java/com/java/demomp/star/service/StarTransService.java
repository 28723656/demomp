package com.java.demomp.star.service;

import java.util.List;

import com.java.demomp.admin.entity.User;
import com.java.demomp.star.entity.StarTrans;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.star.vo.StarTransUserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-12-09
 */
public interface StarTransService extends IService<StarTrans> {

  List<StarTransUserVO> getTransListByUserId(Integer userId);

  void doATrans(StarTrans starTrans);

  List<User> getCanTransUser();
}
