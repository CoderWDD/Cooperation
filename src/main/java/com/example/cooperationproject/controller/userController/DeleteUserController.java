package com.example.cooperationproject.controller.userController;

import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DeleteUserController {

    private final UserService userService;

    public DeleteUserController(UserService userService) {
        this.userService = userService;
    }

    // TODO ： 权限认证
    @PostMapping("/user/deleteUser")
    public Message deleteUser(@RequestBody User user){

        User userRes = userService.FindUserByUsername(user.getUserName());
        if (Objects.isNull(userRes)){
            // 找不到用户
            return ResultUtil.error(StatusCode.BadRequest,"用户名不存在！");
        }

        boolean res = userService.DeleteUserByUsername(user.getUserName());

        if (res){
            // 如果删除成功
            return ResultUtil.success("删除成功！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"删除失败！");
    }
}
