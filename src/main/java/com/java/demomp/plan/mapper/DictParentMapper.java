package com.java.demomp.plan.mapper;

import com.java.demomp.plan.entity.DictParent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
public interface DictParentMapper extends BaseMapper<DictParent> {

    @Select("select * from util_dict_parent where deleted = 0 order by order_list desc limit 1")
    DictParent getMaxOrderList();
}
