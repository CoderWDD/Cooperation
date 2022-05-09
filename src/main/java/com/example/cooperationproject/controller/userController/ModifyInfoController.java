package com.example.cooperationproject.controller.userController;

import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ModifyInfoController {

    private final UserService userService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    public ModifyInfoController(UserService userService, HttpServletRequest request, MyJwtUtil myJwtUtil) {
        this.userService = userService;
        this.request = request;
        this.myJwtUtil = myJwtUtil;
    }


    @ResponseBody
    @PostMapping("/user/modify")
    public Message modifyInfo(@RequestBody User user){
        String token = request.getHeader("token");
        int userId = myJwtUtil.getUserIdFromToken(token);
        String username = myJwtUtil.getUsernameFromToken(token);
        String password = myJwtUtil.getPasswordFromToken(token);
        user.setUserId(userId);
        user.setPassword(password);
        user.setUserName(username);
        boolean res = userService.ModifyInfo(user);
        if (res){
            // 如果修改成功
            return ResultUtil.success("用户信息修改成功！");
        }
        return ResultUtil.error(StatusCode.BadRequest,"新的信息无效！");
    }

    @ResponseBody
    @PostMapping("/user/password/{password}")
    public Message modifyPassword(@PathVariable(value = "password") String password){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        boolean res = userService.ModifyPassword(password, username);

        if (res){
            return ResultUtil.success("密码修改成功！");
        }
        return ResultUtil.error(StatusCode.BadRequest,"密码修改失败！");
    }

}
