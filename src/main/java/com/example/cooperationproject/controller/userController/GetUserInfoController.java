package com.example.cooperationproject.controller.userController;


import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class GetUserInfoController {

    private final UserService userService;

    public GetUserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/getUserInfo")
    public Message getUserInfo(@RequestBody User user){
        User res = userService.FindUserByUsername(user.getUserName());
        if (Objects.isNull(res)){
            return ResultUtil.error(StatusCode.BadRequest,"用户不存在！");
        }
        return ResultUtil.success(res);
    }
}
