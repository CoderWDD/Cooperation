package com.example.cooperationproject.controller.userController;

import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class DeleteUserController {

    private final MyJwtUtil myJwtUtil;

    private final HttpServletRequest request;

    private final UserService userService;

    public DeleteUserController(UserService userService, MyJwtUtil myJwtUtil, HttpServletRequest request) {
        this.userService = userService;
        this.myJwtUtil = myJwtUtil;
        this.request = request;
    }

    @PostMapping("/user/delete")
    public Message deleteUser(){

        String token = request.getHeader("token");

        String username = myJwtUtil.getUsernameFromToken(token);

        User userRes = userService.FindUserByUsername(username);
        if (Objects.isNull(userRes)){
            // 找不到用户
            return ResultUtil.error(StatusCode.BadRequest,"用户信息不存在！");
        }


        boolean res = userService.DeleteUserByUsername(username);

        if (res){
            // 如果删除成功
            return ResultUtil.success("删除成功！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"删除失败！");
    }
}
