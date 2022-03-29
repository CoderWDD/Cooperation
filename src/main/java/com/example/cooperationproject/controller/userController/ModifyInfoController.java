package com.example.cooperationproject.controller.userController;

import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModifyInfoController {

    private final UserService userService;

    public ModifyInfoController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/modifyInfo")
    public Message modifyInfo(@RequestBody User user){
        boolean res = userService.ModifyInfo(user);
        if (res){
            // 如果修改成功
            return ResultUtil.success("用户信息已修改！");
        }
        return ResultUtil.error(StatusCode.BadRequest,"新的信息无效！");
    }
}
