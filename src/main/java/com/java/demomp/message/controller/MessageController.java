package com.java.demomp.message.controller;


import com.java.demomp.message.entity.Message;
import com.java.demomp.message.service.MessageService;
import com.java.demomp.util.IpUtil2;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/message/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping()
    public Result insertMessage(@RequestBody Message message){
        messageService.insertMessage(message);
        return new Result(true,StatusCode.OK,"插入成功");
    }

    @GetMapping
    public Result getMessage(){
       List<Message> messageList = messageService.findMessage();
        return new Result(true,StatusCode.OK,"查询成功",messageList);
    }

}
