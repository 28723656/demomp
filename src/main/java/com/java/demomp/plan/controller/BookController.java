package com.java.demomp.plan.controller;


import com.java.demomp.plan.service.BookService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-11
 */
@RestController
@RequestMapping("/plan/book")
public class BookController {

    @Autowired
    BookService bookService;

    /**
     * 获取所有的图书
     * @return
     */
    @GetMapping("/findAll")
    public Result getAllBook(){

        return new Result(true, StatusCode.OK,"查询成功",bookService.findAll());
    }
}
