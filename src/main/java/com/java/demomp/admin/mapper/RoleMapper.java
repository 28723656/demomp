package com.java.demomp.admin.mapper;

import com.java.demomp.admin.VO.RoleMenuVO;
import com.java.demomp.admin.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT r.*,rm.menu_id as tempMenuId  FROM t_role r, t_role_menu rm  where r.id = rm.role_id and r.deleted = 0 and rm.deleted = 0")
    List<RoleMenuVO> getRoleList();
}
