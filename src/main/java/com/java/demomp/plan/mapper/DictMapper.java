package com.java.demomp.plan.mapper;

import com.java.demomp.plan.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.demomp.plan.entity.DictParent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
public interface DictMapper extends BaseMapper<Dict> {

    @Select("select * from util_dict where deleted = 0 and parent_id = #{parentId} order by order_list desc limit 1")
    Dict getMaxOrderListByParentId(@Param("parentId") Integer parentId);
}
