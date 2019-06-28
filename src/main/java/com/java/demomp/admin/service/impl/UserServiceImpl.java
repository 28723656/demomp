package com.java.demomp.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.admin.VO.UserRoleVO;
import com.java.demomp.admin.entity.User;
import com.java.demomp.admin.entity.UserRole;
import com.java.demomp.admin.mapper.UserMapper;
import com.java.demomp.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    HttpSession session;

    @Autowired
    RedisTemplate redisTemplate;

    // redis存储的最大时间
    static final Integer REDIS_MAX_TIME = 60*60*24*7;


    /**
     * 获取列表
     * @return
     */
    public List<UserRoleVO> getUserList() {
        List<UserRoleVO> userRoleVOList = new ArrayList<>();
        // 放在redis里面
        if(redisTemplate.opsForValue().get("userRoleVOList"+getSessionUserId()) == null){
             userRoleVOList =   baseMapper.getUserList();
            redisTemplate.opsForValue().set("userRoleVOList"+getSessionUserId(),userRoleVOList,REDIS_MAX_TIME);
        }else{
            return (List<UserRoleVO>) redisTemplate.opsForValue().get("userRoleVOList"+getSessionUserId());
        }

        return userRoleVOList;
    }

    /**
     * 添加用户
     * @param userRoleVO
     * @return
     */
    public Integer addUser(UserRoleVO userRoleVO) {

        // 找出phone
        User resultUser = new User().selectOne(new QueryWrapper<User>().eq("phone", userRoleVO.getPhone()));

        // 如果手机号码没有占用，可以注册
        if(resultUser == null){
            // 先在t_user表中添加用户
            User user = setUserProperties(userRoleVO);
            boolean insert = user.insert();

            // 在t_user_role中确定关系
            UserRole userRole = setUserRoleProperties(userRoleVO,user.getId());
            boolean insert1 = userRole.insert();

            if(insert && insert1){
                redisTemplate.delete("userRoleVOList"+getSessionUserId());
                redisTemplate.delete("getUserByRoleList"+getSessionUserId());
                return 1;
            }else {
                return 0;
            }
        }else {
            return -1;
        }




    }

    /**
     * 修改用户
     * @param userRoleVO
     * @return
     */
    public Integer updateUser(UserRoleVO userRoleVO) {

        //在更新之前获取到role_id
        Integer beforeRoleId = new UserRole().selectOne(new QueryWrapper<UserRole>().eq("user_id",userRoleVO.getId())).getRoleId();

        // 修改t_user表中的信息
        User user = setUserProperties(userRoleVO);
        user.setId(userRoleVO.getId());
        boolean b = user.updateById();

        // 之前的roleId和现在的role_id相比，如果变化了，就更新
        if(beforeRoleId!= userRoleVO.getRoleId()){
            // 修改t_user_role表中的信息
            UserRole userRole = setUserRoleProperties(userRoleVO,user.getId());
            UserRole resultUserRole = userRole.selectOne(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            userRole.setId(resultUserRole.getId());
            boolean b1 = userRole.updateById();
        }

        if(b){
            redisTemplate.delete("userRoleVOList"+getSessionUserId());
            redisTemplate.delete("getUserByRoleList"+getSessionUserId());
            return 1;
        }else {
            return 0;
        }


    }


    /**
     * 删除用户通过Id
     * @param id
     * @return
     */
    public Integer deleteUserById(Integer id) {

        // 如果是管理员的话，滚回去，敢删我？
        User resultUser = new User().selectById(id);
        if(resultUser.getAdmin() == 1){
            return -1;
        }


        // 删除t_user表
        User user = new User();
        boolean b = user.setId(id).deleteById();

        // 删除 t_user_role表
        UserRole userRole = new UserRole();
        boolean b1 = userRole.delete(new QueryWrapper<UserRole>().eq("user_id", id));

        if(b && b1){
            redisTemplate.delete("userRoleVOList"+getSessionUserId());
            redisTemplate.delete("getUserByRoleList"+getSessionUserId());
            return 1;
        }else {
            return 0;
        }

    }

    /**
     * 通过role找到用户
     * @return
     */
    public List<UserRoleVO> getUserByRole() {
        List<UserRoleVO> getUserByRoleList = new ArrayList<>();
        if(redisTemplate.opsForValue().get("getUserByRoleList"+getSessionUserId())==null){
            getUserByRoleList = baseMapper.getUserByRole();
            redisTemplate.opsForValue().set("getUserByRoleList"+getSessionUserId(),getUserByRoleList,REDIS_MAX_TIME);
        }else{
            return (List<UserRoleVO>) redisTemplate.opsForValue().get("getUserByRoleList"+getSessionUserId());
        }
        return getUserByRoleList;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    public User login(User user) {
        User resultUser = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", user.getPhone()).eq("password", Md5Util.getMD5WithSalt(user.getPassword())));
        return resultUser;
    }

    /**
     * 通过
     * @return
     */
    public List<String> getMenusByUserId(Integer id) {
       return  baseMapper.getMenusByUserId(id);
    }


    // 抽取一个公共方法，设置基本属性 t_user
    public User setUserProperties(UserRoleVO userRoleVO){
        User user = new User();
        user.setPhone(userRoleVO.getPhone());
        user.setNickName(userRoleVO.getNickName());
        // 由于我在修改的时候把密码给隐藏了，所以这里要做一下判断
        if(userRoleVO.getPassword() != null){
            user.setPassword(Md5Util.getMD5WithSalt(userRoleVO.getPassword()));
        }
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

    public Integer getSessionUserId(){
        User userSession = (User) session.getAttribute("user");
        if(userSession!=null){
            return userSession.getId();
        }else {
            return 0;
        }
    }

}
