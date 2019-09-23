package com.java.demomp.mq;

import com.java.demomp.DemompApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 风往西边吹丶
 * @create 2019-09-23 14:05
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    public  void sendMsg1(){
        rabbitTemplate.convertAndSend("itcast","直接模式测试");
    }


    /**
     * 分裂模式
     */
    @Test
    public  void sendMsg2(){
        rabbitTemplate.convertAndSend("poker","","分裂模式");
    }



    /**
     * 主题模式
     */
    @Test
    public  void sendMsg3(){
        rabbitTemplate.convertAndSend("topicxr","good.abc.log","主题模式");
    }



}
