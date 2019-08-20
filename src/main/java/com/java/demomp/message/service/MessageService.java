package com.java.demomp.message.service;

import com.java.demomp.message.entity.Message;
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
public interface MessageService extends IService<Message> {

    void insertMessage(Message message);

    List<Message> findMessage();
}
