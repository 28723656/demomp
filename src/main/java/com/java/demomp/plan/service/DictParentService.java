package com.java.demomp.plan.service;

import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.entity.DictParent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
public interface DictParentService extends IService<DictParent> {

    List<Dict> getDictListByParentCode(String code);
}
