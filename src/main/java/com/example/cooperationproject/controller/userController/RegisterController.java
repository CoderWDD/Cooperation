package com.example.cooperationproject.controller.userController;

import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/user/register")
    public Message register(@RequestBody User user){
        boolean res = userService.Register(user);
        if (res){
            return ResultUtil.success("注册成功！");
        }else {
            return ResultUtil.error(400,"用户名已存在！");
        }
    }
}
