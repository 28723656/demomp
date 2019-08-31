package com.java.demomp.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.entity.DictParent;
import com.java.demomp.plan.mapper.DictParentMapper;
import com.java.demomp.plan.service.DictParentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.plan.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
@Service
public class DictParentServiceImpl extends ServiceImpl<DictParentMapper, DictParent> implements DictParentService {

    @Autowired
    DictService dictService;

    /**
     * 通过父类 code获取子类 List集合
     * @param code
     * @return
     */
    public List<Dict> getDictListByParentCode(String code) {
        DictParent parent = baseMapper.selectOne(new QueryWrapper<DictParent>().eq("code", code));
       return  dictService.list(new QueryWrapper<Dict>().eq("parent_id",parent.getId()));
    }
}
