package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.pojo.authentication.LoginUser;
import com.example.cooperationproject.mapper.UserMapper;
import com.example.cooperationproject.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean Register(User user) {
        // 不允许用户重名
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name",user.getUserName());
        User user1 = getOne(wrapper);

        if (Objects.isNull(user1)){
            // 如果用户不存在，则允许注册
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            boolean res = save(user);
            return res;
        }
        return false;
    }

    @Override
    public boolean Login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);
        if (Objects.isNull(user)){
            // 如果用户不存在
            return false;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        return matches;
    }

    @Override
    public boolean ModifyInfo(User user) {
        String username = user.getUserName();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User dbUser = getOne(wrapper);
        if (Objects.isNull(dbUser)){
            return false;
        }
        try {
            // 有可能传入的数据是无效的
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newEncodePassword = passwordEncoder.encode(user.getPassword());
            dbUser.setPassword(newEncodePassword);
            dbUser.setAvatar(user.getAvatar());
            dbUser.setDepartment(user.getDepartment());
            dbUser.setDeleteFlag(user.getDeleteFlag());
            dbUser.setDescription(user.getDescription());
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setNickName(user.getNickName());
            dbUser.setPhone(user.getPhone());
            dbUser.setSex(user.getSex());
        }catch (Exception e){
            return false;
        }

        updateById(dbUser);
        // 能跑到这里来，一定是修改成功了
        return true;
    }

    @Override
    public boolean DeleteUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);
        if (Objects.isNull(user)) return false;
        return removeById(user.getUserId());
    }

    @Override
    public User FindUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);
        return user;
    }

    @Override
    public String FindUsernameByUserId(Integer userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        User user = getOne(wrapper);
        return user.getUserName();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 每次有访问，都会调用到这里

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User user = getOne(wrapper);

        System.out.println("wqeqweqweqweqweqweqweqweqweqweqweqwe");

        // 如果用户不在数据库，则抛出异常
        if (Objects.isNull(user) || StringUtils.isNullOrEmpty(user.getUserName())){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // TODO ： 添加admin和普通user等的身份认证
        // 可以尝试将用户的权限信息封装到一个实体类中，然后放进这里
        // 就可以利用SpringSecurity的授权鉴权机制了
        LoginUser loginUser = new LoginUser(user,new ArrayList<String>());
        return loginUser;
    }
}
