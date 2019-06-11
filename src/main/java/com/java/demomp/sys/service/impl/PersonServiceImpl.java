package com.java.demomp.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.sys.entity.Person;
import com.java.demomp.sys.mapper.PersonMapper;
import com.java.demomp.sys.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
        String tempPassword = "2872";
        String salt = "lost丶wind";
        String password = DigestUtils.md5DigestAsHex((tempPassword+salt).getBytes());
        person.setPassword(password);
        boolean insert = person.insert();

        if(insert){
            System.out.println(person);
            return person;
        }
        return null;
    }

    public List<Person> getPersonList() {

        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        //1--------- allEq  map里面全都为等于
//        Map<String,Object> map = new HashMap<>();
//        map.put("user_name","张三");
//        map.put("password",null);
//        queryWrapper.allEq(map,false);
//
        //2-------eq  ne gt  ge  lt  le  大于小于符号
//        queryWrapper.ne("user_name","xia");

        //3-------between  在。。。之间  同理 notBetween
//        queryWrapper.between("age",13,24);

        //4------like 全like  同理 notLike likeLeft--> %张三  likeRight
//        queryWrapper.like("user_name","张三");

        //5------ isNotNull  查询非空的
//        queryWrapper.isNotNull("user_name");

        //6------ in  notIn
//        queryWrapper.in("age",12,42,13,18);

        //7------ inSql notInSql
//        queryWrapper.inSql("age","13,23,42,51");  // age in (1,2,3,4,5,6)
//        queryWrapper.inSql("age","select age from t_person where user_name like '张三%'"); // age in(select )

        //8------ groupBy  orderBy
//        queryWrapper.groupBy("id","user_name");

        //9------ or *********** and(重要)
//        queryWrapper.eq("user_name","张三").or().lt("age",18);// user_name =“张三” or age <18
//        queryWrapper.or(i -> i.eq("user_name","张三").eq("age",18));

        //10----- last 拼接到最后
//        queryWrapper.last("limit 10")；

        //11----- exists 拼接exists (不是很懂)
//        queryWrapper.exists("select id from table where age<13");

        //12 -----select  只选出几个字段
      //  queryWrapper.select("id","user_name","age").lt("age",18);



        queryWrapper.lt("age",18);



        List<Person> people = baseMapper.selectList(queryWrapper);

        return people;
    }

    public Integer deletePersonList() {

        UpdateWrapper<Person> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lt("age",14);
        int delete = baseMapper.delete(updateWrapper);
        return delete;
    }

    public Boolean login(Person person) {
        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        String tempPassword = person.getPassword();
        String salt = "lost丶wind";
        queryWrapper.eq("user_name",person.getUserName()).eq("password",DigestUtils.md5DigestAsHex((tempPassword+salt).getBytes()));
        Person person1 = baseMapper.selectOne(queryWrapper);
        if(person1!=null){
            return true;
        }
        return false;
    }
}
