package com.java.demomp.plan.controller;


import com.java.demomp.plan.entity.Book;
import com.java.demomp.plan.service.BookService;
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
    @GetMapping()
    public Result getAllBook(){
        return new Result(true, StatusCode.OK,"查询成功",bookService.findAll());
    }

    @DeleteMapping("/{id}")
    public Result deleteBookById(@PathVariable Integer id){
       int resultNum =  bookService.deleteBookById(id);
       if(resultNum>0){
           return new Result(true, StatusCode.OK,"删除成功",resultNum);
       }else{
           return new Result(false, StatusCode.ERROR,"删除失败",resultNum);
       }
    }

    /**
     * 修改
     * @param book
     * @return
     */
    @PutMapping()
    public Result updateById(@RequestBody Book book){
        boolean b = bookService.updateById(book);
        if(b){
            return new Result(true, StatusCode.OK,"修改成功");
        }else{
            return new Result(false, StatusCode.ERROR,"修改失败");
        }
    }


    /**
     * 添加
     * @param book
     * @return
     */
    @PostMapping()
    public Result addBook(@RequestBody Book book){
        boolean b = bookService.save(book);
        if(b){
            return new Result(true, StatusCode.OK,"添加成功");
        }else{
            return new Result(false, StatusCode.ERROR,"添加失败");
        }
    }

    @GetMapping("/{bookName}")
    public Result getBookByBookName(@PathVariable String bookName){
        List<Book> bookList = bookService.getBookByBookName(bookName);
        if(bookList!=null){
            return new Result(true, StatusCode.OK,"查询成功",bookList);
        }else {
            return new Result(true, StatusCode.OK,"数据为空或者查询失败");
        }
    }



}
