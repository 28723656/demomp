package com.java.demomp.password.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.service.UserService;
import com.java.demomp.game.entity.GameMyMoney;
import com.java.demomp.game.service.GameMyMoneyService;
import com.java.demomp.password.entity.Password;
import com.java.demomp.password.mapper.PasswordMapper;
import com.java.demomp.password.service.PasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.util.LocalDateTimeUtil;
import com.java.demomp.util.Md5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
public class PasswordServiceImpl extends ServiceImpl<PasswordMapper, Password> implements PasswordService {

  /**
   * 加密密码
   * @param password
   * @param userId
   * @return
   */

  @Autowired
  UserService userService;

  @Autowired
  GameMyMoneyService gameMyMoneyService;

  // 设置密码
  public boolean addPassword(Password password, Integer userId) {
    // 我的加密方式 4399-> xxx    xxx+用户密码+idWork+盐->xxx2


    // 1.获取当前时间,并设置开始日期和结束日期
    //  LocalDateTime now = LocalDateTime.now().minusDays(3);
    LocalDateTime now = LocalDateTime.now();
    password.setBeginDate(now);
    password.setEndDate(now.plusDays(password.getDays()));
    String idWorker = IdWorker.getIdStr();

    // 2.首先通过用户的userId获得用户的密码
    String userPassword = userService.getById(userId).getPassword();
    // 2.1 第一次加密
    String firstMd5 = Md5Util.getMD5(password.getPassword());
    // 2.2 第二次加密
    String toBeMd5 = firstMd5 + userPassword + idWorker;
    password.setPassword(Md5Util.getMD5WithSalt(toBeMd5));
    password.setUserId(userId);
    password.setIdWorker(idWorker);

    int i = baseMapper.insert(password);
    if (i > 0) {
      return true;
    } else {
      return false;
    }


  }

  /**
   * 展示数据
   * @param userId
   * @return
   */
  public List<Password> getInfo(Integer userId) {
    // 1.通过userId找到用户的加密信息
    return baseMapper.selectList(new QueryWrapper<Password>().eq("user_id", userId));
  }

  /**
   * 解开密码
   * @param password
   * @param userId
   * @return
   */
  public Password openPassword(Password password, Integer userId) {
    // 解开密码

    // 密码实体
    Password passwordEntity = baseMapper.selectById(password.getId());

    if (passwordEntity.getRealPassword() != null) {
      return passwordEntity;
    }

    // 获取用户密码
    User user = userService.getById(userId);
    String userPassword = user.getPassword();

    // 如果作弊，修改percent,来请求这个url,还是会返回空
    if (LocalDateTime.now().isBefore(passwordEntity.getEndDate())) {
      // fixme：新增，有时候我确实是受不了，想知道密码，所以我就在这里留一个后门
      // fixme: 但是也是有一定的条件的，就是要扣除相应的钻石，如果钻石数量不够，我是不会解除的
      List<GameMyMoney> gameMyMoneyList = gameMyMoneyService.list(new QueryWrapper<GameMyMoney>().eq("user_id", userId));
      if (CollectionUtils.isNotEmpty(gameMyMoneyList)) {
        Integer moneyNum = gameMyMoneyList.stream().filter(item -> item.getType() == 2).map(item -> item.getMoneyNum()).findFirst().get();
        Duration between = Duration.between(LocalDateTime.now(), passwordEntity.getEndDate());
        int betweenHours = (int) between.toHours();
        if (moneyNum >= betweenHours * 5) {
          // 扣除钻石
          GameMyMoney gameMyMoney = gameMyMoneyList.stream().filter(item -> item.getType() == 2).findFirst().get();
          gameMyMoney.setMoneyNum(gameMyMoney.getMoneyNum() - betweenHours * 5);
          gameMyMoneyService.updateById(gameMyMoney);
        }else {
          return null;
        }
      }
    }


    // 我的加密方式 4399-> xxx    xxx+用户密码+IdWorker+盐->xxx2
    // 解题思路：从1000-9999开始 对密码进行加密，然后在加密是否和结果一致，一致就停止返回结果
    for (int i = 1000; i <= 9999; i++) {
      String firstMd5 = Md5Util.getMD5(i + "");
      String realPassword = Md5Util.getMD5WithSalt(firstMd5 + userPassword + passwordEntity.getIdWorker());
      // 如果两个密码一致
      if (realPassword.equals(passwordEntity.getPassword())) {
        passwordEntity.setRealPassword(i + "");
        break;
      }
    }
    if (passwordEntity.getRealPassword() == null) {
      return null;
    } else {
      baseMapper.updateById(passwordEntity);
      return passwordEntity;
    }


  }

  /**
   * 删除记录
   * @param id
   * @return
   */
  public Integer deletePasswordById(Integer id) {
    return baseMapper.deleteById(id);
  }
}
