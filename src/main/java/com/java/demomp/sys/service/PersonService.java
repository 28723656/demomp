package com.java.demomp.sys.service;

import com.java.demomp.sys.entity.Person;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-10
 */
public interface PersonService extends IService<Person> {


    Person save();

    List<Person> getPersonList();

    Integer deletePersonList();

    Boolean login(Person person);
}
