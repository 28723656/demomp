package com.java.demomp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 风往西边吹丶
 * @create 2019-08-20 19:10
 */
public class LocalDateTimeUtil {

    // 格式化LocalDateTime的时间
    public static String getFormatTimeString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter); // "2019-08-20 19:12:23"
    }

}
