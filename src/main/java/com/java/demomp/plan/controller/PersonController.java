package com.java.demomp.plan.controller;


import com.java.demomp.plan.entity.Person;
import com.java.demomp.plan.service.PersonService;
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
    public Result add(){
       return new Result(true, StatusCode.OK,"添加成功", personService.save());

    }

    @GetMapping("/find")
    public Result find(){
        return new Result(true,StatusCode.OK,"查找结果",personService.getPersonList());
    }

    @DeleteMapping("/delete")
    public Result delete(){
        return new Result(true,StatusCode.OK,"删除成功",personService.deletePersonList());
    }

    @GetMapping("/login")
    public Result login(Person person){
        Boolean login = personService.login(person);
        if(login){
            return new Result(true,StatusCode.OK,"登陆成功",personService.login(person));
        }else{
            return new Result(false,StatusCode.LOGINERROR,"登陆失败",personService.login(person));
        }

    }

    @PutMapping("/update")
    public Result update(Person person){
        return new Result(true,StatusCode.OK,"修改成功",personService.updateUserById(person));
    }

}
