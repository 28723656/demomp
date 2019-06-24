package com.java.demomp.admin.VO;

import com.java.demomp.admin.entity.Menu;
import com.java.demomp.admin.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleMenuVO extends Role {

    Integer  tempMenuId;

    List<Integer> menuId;
}
