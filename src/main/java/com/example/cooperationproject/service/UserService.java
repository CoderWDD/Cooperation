package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends IService<User>, UserDetailsService {
    boolean Register(User user);
    boolean Login(String userId,String password);

    boolean ModifyInfo(User user);

    boolean ModifyPassword(String password,String username);

    boolean DeleteUserByUsername(String username);

    boolean DeleteUserByUserId(Integer userId);

    User FindUserByUsername(String username);

    String FindUsernameByUserId(Integer userId);

    boolean UploadAvatar(byte[] newAvatar,Integer userId);
}
