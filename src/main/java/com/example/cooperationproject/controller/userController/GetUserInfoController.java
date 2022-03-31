package com.example.cooperationproject.controller.userController;


import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class GetUserInfoController {

    private final UserService userService;

    public GetUserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/get/{username}")
    public Message getUserInfo(@PathVariable(value = "username") String username){
        User res = userService.FindUserByUsername(username);
        if (Objects.isNull(res)){
            return ResultUtil.error(StatusCode.BadRequest,"用户不存在！");
        }
        return ResultUtil.success(res);
    }
}
