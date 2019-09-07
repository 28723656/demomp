package com.java.demomp.plan.service;

import com.java.demomp.plan.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.demomp.plan.entity.DictParent;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
public interface DictService extends IService<Dict> {

    Dict getMaxOrderListByParentId(Integer parentId);

}
