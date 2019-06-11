package com.java.demomp.plan.controller;

import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 公共异常处理类
 * @author 风往西边吹丶
 * @create 2019-01-10 10:43
 */

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
