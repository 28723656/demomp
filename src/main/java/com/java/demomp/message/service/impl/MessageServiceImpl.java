package com.java.demomp.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.service.UserService;
import com.java.demomp.message.entity.Message;
import com.java.demomp.message.mapper.MessageMapper;
import com.java.demomp.message.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    UserService userService;

    public void insertMessage(Message message) {
        message.setUsername( userService.getById(message.getUserId()).getNickName());
        baseMapper.insert(message);
    }

    public List<Message> findMessage() {
       return  baseMapper.selectList(new QueryWrapper<Message>().orderByDesc("create_time"));
    }
}
