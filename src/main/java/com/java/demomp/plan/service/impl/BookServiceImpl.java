package com.java.demomp.plan.service.impl;

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

    @Override
    public List<Book> findAll() {
        return baseMapper.selectList(null);
    }
}
