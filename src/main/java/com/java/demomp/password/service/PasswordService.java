package com.java.demomp.password.service;

import com.java.demomp.password.entity.Password;
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
public interface PasswordService extends IService<Password> {

    // 添加密码
    boolean addPassword(Password password, Integer userId);

    // 展示数据
    List<Password> getInfo(Integer userId);

    Password openPassword(Password password, Integer userId);

    Integer deletePasswordById(Integer id);
}
