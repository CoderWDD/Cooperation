package com.example.cooperationproject.controller.userController;

import com.example.cooperationproject.entity.UserLogin;
import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.TranslateUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    private final UserService userService;

    private final MyJwtUtil myJwtUtil;

    public LoginController(UserService userService, MyJwtUtil myJwtUtil) {
        this.userService = userService;
        this.myJwtUtil = myJwtUtil;
    }

    /**
     * 登录账号，返回token
     * @param userLogin
     * @return
     */
    @ResponseBody
    @PostMapping("/user/login")
    public Message loginController(@RequestBody UserLogin userLogin){
        boolean res = userService.Login(userLogin.getUsername(), userLogin.getPassword());

        if (res){
            // 如果数据库中有user

            // 则从数据库中取出当前用户的所有信息存在token中
            User userInstance = userService.FindUserByUsername(userLogin.getUsername());

            // 存在token中的password，后面会再加密，避免二次加密
            userInstance.setPassword(userLogin.getPassword());

            Map<String,Object> map = TranslateUtil.UserToMap(userInstance);

            String token = myJwtUtil.generateToken(map, userInstance.getUserName());

            return ResultUtil.success("token: " + token);
        }
        return ResultUtil.error(StatusCode.UnAuthorized,"账号或密码错误");
    }
}
