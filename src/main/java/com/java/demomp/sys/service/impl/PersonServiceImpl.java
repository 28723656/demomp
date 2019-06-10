package com.java.demomp.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.sys.entity.Person;
import com.java.demomp.sys.mapper.PersonMapper;
import com.java.demomp.sys.service.PersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-10
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

    public Person save() {
        Person person = new Person();
        person.setUserName("xia");
        person.setPassword("oo");
        boolean insert = person.insert();

        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",1);
        List<Person> people = baseMapper.selectList(queryWrapper);
        Person person1 = people.get(0);
        System.out.println(person1);

        if(insert){
            System.out.println(person);
            return person;
        }
        return null;
    }
}
