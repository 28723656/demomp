package com.java.demomp.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.plan.entity.Book;
import com.java.demomp.plan.mapper.BookMapper;
import com.java.demomp.plan.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-11
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    public List<Book> findAll() {
        return baseMapper.selectList(null);
    }

    public int deleteBookById(Integer id) {
        return baseMapper.deleteById(id);
    }

    public List<Book> getBookByBookName(String bookName) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("book_name",bookName);
        List<Book> bookList = baseMapper.selectList(queryWrapper);
        return bookList;
    }
}
