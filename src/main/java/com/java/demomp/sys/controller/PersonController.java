package com.java.demomp.sys.controller;


import com.java.demomp.sys.entity.Person;
import com.java.demomp.sys.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-10
 */
@RestController
@RequestMapping("/sys/person")
public class PersonController {

    @Autowired
    PersonService personService;

/*    // 添加
    @PostMapping("/save")
   public void add(){
       Person person = new Person();
       Random random = new Random();
       int num = random.nextInt(1000);
       int num2 = random.nextInt(1000);
       person.setUserName("张三"+num);
       person.setPassword("密码"+num2);
       // 第一种增加的方法
        boolean save = personService.save(person);
        System.out.println("--------");
        if(save){
            System.out.println("ok");
        }
    }*/


    // 添加
    @PostMapping("/save")
    public Person add(){
       return  personService.save();
    }

    @GetMapping("/find")
    public List<Person> find(){
        return personService.getPersonList();
    }

    @DeleteMapping("/delete")
    public Integer delete(){
        return personService.deletePersonList();
    }

    @GetMapping("/login")
    public boolean login(Person person){
        return personService.login(person);
    }

    @PutMapping("/update")
    public boolean update(Person person){
        return personService.updateUserById(person);
    }

}
