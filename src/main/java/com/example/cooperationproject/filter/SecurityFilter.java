package com.example.cooperationproject.filter;

import com.example.cooperationproject.constant.AllowUriContanst;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.service.UidTidAuidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Component
public class SecurityFilter implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private MyJwtUtil myJwtUtil;

    @Autowired
    private UidPidService uidPidService;

    @Autowired
    private UidTidAuidService uidTidAuidService;


    // 当接收到一个http请求时, filterSecurityInterceptor会调用的方法. 参数object是一个包含url信息的HttpServletRequest实例. 这个方法要返回请求该url所需要的所有权限集合。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = ((FilterInvocation) object);
        HttpServletRequest request = filterInvocation.getHttpRequest();

        String requestURI = request.getRequestURI();

        // uri : 相对路径 eg: /user/login
        // url : 绝对路径 eg: http://127.0.0.1:3345/user/login


        // 如果是白名单中的uri，直接放行
        List<String> uriList = AllowUriContanst.getUriList();

        for (String e : uriList){
            if (Objects.isNull(requestURI) ||  e.equals(requestURI)){
                return null;
            }
        }

        // 到这里，说明request中一定要带token
        // 如果没带，则说明请求错误，直接返回，会有认证的filter处理
        String token = request.getHeader("token");
        if (StringUtils.isNullOrEmpty(token)){
            return null;
        }

        // 如果有token，则获取userId
        int userID = myJwtUtil.getUserIdFromToken(token);

        // 才数据库中获取当前userId所能进行的权限操作





        // 不可以在这个类中获取request中的body，因为流只能读一次，且这里的object是多个对象的组合

        // TODO : 对不同身份的用户在url上

        return null;
    }

    // Spring容器启动时自动调用, 一般把所有请求与权限的对应关系也要在这个方法里初始化, 保存在一个属性变量里。
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // 指示该类是否能够为指定的方法调用或Web请求提供ConfigAttributes。
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
