package com.example.cooperationproject.controller.userController;


import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class GetUserInfoController {

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final UserService userService;

    public GetUserInfoController(UserService userService, HttpServletRequest request, MyJwtUtil myJwtUtil) {
        this.userService = userService;
        this.request = request;
        this.myJwtUtil = myJwtUtil;
    }

    /**
     * 根据username获取user信息
     * @param username
     * @return
     */
    @ResponseBody
    @GetMapping("/user/get/{username}")
    public Message getUserInfo(@PathVariable(value = "username") String username){
        User res = userService.FindUserByUsername(username);
        if (Objects.isNull(res)){
            return ResultUtil.error(StatusCode.BadRequest,"用户不存在！");
        }
        return ResultUtil.success(res);
    }

    @ResponseBody
    @GetMapping("/user/get/current")
    public Message getUserCurrentInfo(){
        String token = request.getHeader("token");

        String username = myJwtUtil.getUsernameFromToken(token);

        User user = userService.FindUserByUsername(username);
        if (Objects.isNull(user)){
            return ResultUtil.error(StatusCode.BadRequest,"用户不存在！");
        }
        return ResultUtil.success(user);
    }
}
