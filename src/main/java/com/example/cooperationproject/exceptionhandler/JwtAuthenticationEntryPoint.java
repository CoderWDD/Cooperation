package com.example.cooperationproject.exceptionhandler;

import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;


/**
 * 统一处理未认证的请求
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        Message message = ResultUtil.error(StatusCode.UnAuthorized, "未认证！");
        writer.print(message);
    }
}
