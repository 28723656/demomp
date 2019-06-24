package com.java.demomp.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.entity.UserRole;
import com.java.demomp.admin.mapper.UserMapper;
import com.java.demomp.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 获取列表
     * @return
     */
    public List<UserRoleVO> getUserList() {
        List<UserRoleVO> userRoleVOList =   baseMapper.getUserList();
        return userRoleVOList;
    }

    /**
     * 添加用户
     * @param userRoleVO
     * @return
     */
    public Integer addUser(UserRoleVO userRoleVO) {

        // 先在t_user表中添加用户
       User user = setUserProperties(userRoleVO);
        boolean insert = user.insert();

        // 在t_user_role中确定关系
        UserRole userRole = setUserRoleProperties(userRoleVO,user.getId());
        boolean insert1 = userRole.insert();

        if(insert && insert1){
            return 1;
        }else {
            return 0;
        }


    }

    /**
     * 修改用户
     * @param userRoleVO
     * @return
     */
    public Integer updateUser(UserRoleVO userRoleVO) {

        // 修改t_user表中的信息
        User user = setUserProperties(userRoleVO);
        user.setId(userRoleVO.getId());
        boolean b = user.updateById();

        if(user.getId() != userRoleVO.getRoleId()){
            // 修改t_user_role表中的信息
            UserRole userRole = setUserRoleProperties(userRoleVO,user.getId());
            UserRole resultUserRole = userRole.selectOne(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            userRole.setId(resultUserRole.getId());
            boolean b1 = userRole.updateById();
        }


        return 1;

    }


    /**
     * 删除用户通过Id
     * @param id
     * @return
     */
    public Integer deleteUserById(Integer id) {
       // 删除t_user表
        User user = new User();
        boolean b = user.setId(id).deleteById();

        // 删除 t_user_role表
        UserRole userRole = new UserRole();
        boolean b1 = userRole.delete(new QueryWrapper<UserRole>().eq("user_id", id));

        if(b && b1){
            return 1;
        }else {
            return 0;
        }

    }


    // 抽取一个公共方法，设置基本属性 t_user
    public User setUserProperties(UserRoleVO userRoleVO){
        User user = new User();
        user.setPhone(userRoleVO.getPhone());
        user.setNickName(userRoleVO.getNickName());
        user.setPassword(userRoleVO.getPassword());
        user.setDescription(userRoleVO.getDescription());
        return user;
    }



    // 抽取一个公共方法，设置基本属性 t_user_role
    public UserRole setUserRoleProperties(UserRoleVO userRoleVO,Integer userId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(userRoleVO.getRoleId());
        return userRole;
    }
}
