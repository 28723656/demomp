package com.java.demomp.plan.service.impl;

import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.entity.DictParent;
import com.java.demomp.plan.mapper.DictMapper;
import com.java.demomp.plan.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    public Dict getMaxOrderListByParentId(Integer parentId) {
        return baseMapper.getMaxOrderListByParentId(parentId);
    }
}
