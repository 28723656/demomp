package com.java.demomp.util;

import org.springframework.util.DigestUtils;

import java.util.Random;

public class Md5Util {

    public static String salt ="lost丶wind";

    // 有盐
    public static String getMD5WithSalt(String str){
        //原来的加盐的方式
        String base = str+salt;
        // 现在的加盐方式
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    // 无盐
    public static String getMD5(String str){
       return DigestUtils.md5DigestAsHex(str.getBytes());
    }

}
