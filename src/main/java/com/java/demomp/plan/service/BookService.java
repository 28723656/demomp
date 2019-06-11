package com.java.demomp.plan.service;

import com.java.demomp.plan.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-11
 */
public interface BookService extends IService<Book> {

   List<Book> findAll();
}
