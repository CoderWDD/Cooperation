package com.example.cooperationproject.filter;

import com.example.cooperationproject.pojo.authentication.LoginUser;
import com.example.cooperationproject.service.impl.UserServiceImpl;
import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.utils.MyJwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * 认证过滤器
 * 所有的请求过来，都得经过这个过滤器
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyJwtUtil myJwtUtil;

    private final UserServiceImpl userService;

    public JwtRequestFilter(MyJwtUtil myJwtUtil, UserServiceImpl userService) {
        this.myJwtUtil = myJwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(ConstantFiledUtil.AUTHORIZATION_TOKEN);
        if (!StringUtils.hasText(token)){
            // 如果没有携带token，则直接放行
            // 后面的过滤器不会让其通过的，故不用处理
            filterChain.doFilter(request,response);
            return;
        }

        String username = null;
        String password = null;

        try {
            username = myJwtUtil.getUsernameFromToken(token);
            password = myJwtUtil.getPasswordFromToken(token);

        }catch (Exception e){
            throw new RuntimeException("token非法!!!!");
        }

        if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null){
            LoginUser loginUser = (LoginUser) userService.loadUserByUsername(username);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


            if (myJwtUtil.isTokenValidate(token,loginUser.getUser()) && !Objects.isNull(password) && passwordEncoder.matches(password,loginUser.getUser().getPassword())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }
        }
        // 放行
        filterChain.doFilter(request,response);
    }
}
