package com.java.demomp.admin.service.impl;

import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.mapper.UserMapper;
import com.java.demomp.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
