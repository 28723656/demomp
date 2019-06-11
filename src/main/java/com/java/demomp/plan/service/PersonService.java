package com.java.demomp.plan.service;

import com.java.demomp.plan.entity.Person;
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

    boolean updateUserById(Person person);
}
