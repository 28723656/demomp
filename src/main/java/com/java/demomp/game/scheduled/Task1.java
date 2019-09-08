package com.java.demomp.game.scheduled;

import com.java.demomp.game.entity.GameRewardDay;
import com.java.demomp.game.service.GameMyCardService;
import com.java.demomp.game.service.GameRewardDayService;
import com.java.demomp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 风往西边吹丶
 * @create 2019-09-04 21:14
 */

@Component
public class Task1 {

    @Autowired
    GameMyCardService gameMyCardService;

    @Autowired
    GameRewardDayService gameRewardDayService;


    /**
     *  这个是用来测试的，现在不用了，每5秒执行一次
     */
/*    @Scheduled(cron = "0/5 * * * * ?")
    public void test1() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("现在时刻："+localDateTime);
    }*/



    /**
     * 每天凌晨0点1秒获取 用户的卡片技能3对应的钥匙数量
     *   0 0/5 * * * ?   每5分钟执行一次
     *
     *   1 0 0 * * ?   每天凌晨1秒执行
     *
     *   每天早上6点执行
     */
    @Scheduled(cron = "1 0 0 * * ?")
   public void getRewardEveryDay(){
        System.out.println("发福利啦--------------------");
        // 1.从视图中查找有资格获取 奖励的用户
      List<GameRewardDay> gameRewardDayList =  gameMyCardService.getEveryDayReward();

      // 2.把用户id不为空的数据写入 reward_day表中
        List<GameRewardDay> newData = new ArrayList<>();
        for(int i=0;i<gameRewardDayList.size();i++){
            GameRewardDay gameRewardDay = gameRewardDayList.get(i);
            if(gameRewardDay.getUserId() != null && gameRewardDay.getTopCount().intValue()>=1){
                Integer randomNum = RandomUtil.getRandomFromTo(gameRewardDay.getLowCount().intValue(), gameRewardDay.getTopCount().intValue());
                gameRewardDay.setRewardCount(randomNum);
                newData.add(gameRewardDay);
            }
        }
        boolean b = gameRewardDayService.saveBatch(newData);


   }

}
