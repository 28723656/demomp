package com.java.demomp.util;

import org.springframework.util.DigestUtils;

public class RandomUtil {


    /**
     * 获取一个随机数 [beginNum,endNum]
     * @param beginNum
     * @param endNum
     * @return
     */
    public static Integer getRandomFromTo(Integer beginNum ,Integer endNum){
        return  (int)(Math.random() * (endNum - beginNum+1) + beginNum);
    }

    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            Integer randomNum = getRandomFromTo(3, 10);
            if((i+1) %50 == 0){
                System.out.println(randomNum+" ");
            }else {
                System.out.print(randomNum+" ");
            }
        }
    }

}
