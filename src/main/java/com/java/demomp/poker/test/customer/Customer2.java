package com.java.demomp.poker.test.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 风往西边吹丶
 * @create 2019-09-23 14:14
 */
@Component
@RabbitListener(queues = "test1")
public class Customer2 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("-----分裂模式test1："+msg);
    }

}
