package com.java.demomp.sys.service.impl;

import com.java.demomp.sys.entity.Person;
import com.java.demomp.sys.mapper.PersonMapper;
import com.java.demomp.sys.service.IPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-10
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

}
