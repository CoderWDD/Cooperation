package com.example.cooperationproject.filter;

import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.TranslateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class SecurityFilter implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private MyJwtUtil myJwtUtil;


    /**
     * 返回请求的资源需要的角色权限列表
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    // 当接收到一个http请求时, filterSecurityInterceptor会调用的方法. 参数object是一个包含url信息的HttpServletRequest实例. 这个方法要返回请求该url所需要的所有权限集合。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 不可以在这个类中获取request中的body，因为流只能读一次，且这里的object是多个对象的组合
        FilterInvocation filterInvocation = ((FilterInvocation) object);
        HttpServletRequest request = filterInvocation.getHttpRequest();

        String requestURI = request.getRequestURI();

        // uri : 相对路径 eg: /user/login
        // url : 绝对路径 eg: http://127.0.0.1:3345/user/login

        String authentication = TranslateUtil.UriToAuthentication(requestURI);

        // uri没有匹配的权限，直接往下传，后面的filter会拦截
        if (authentication == null) return null;

        List<ConfigAttribute> authenticationList = new ArrayList<>();

        // 将权限包装成 ConstantFiledUtil.ITEM + ":" + itemId + ":"+ authentication.getAnName() 格式
        String[] strings = authentication.split("/");
        if (strings.length < 3){
            return null;
        }
        String preString = strings[0] + ":" + strings[2] + ":";


        // 这个switch可以利用不收到break的特性减少代码行数，但是为了可读性，还是写
        switch (authentication){
            case ConstantFiledUtil.USER:
                // 如果是只需要user的权限，就把所有的权限放进去，表示所有的人都可以访问
                authenticationList.add(new SecurityConfig(preString + ConstantFiledUtil.USER));
                authenticationList.add(new SecurityConfig(preString + ConstantFiledUtil.ADMIN));
                authenticationList.add(new SecurityConfig(preString + ConstantFiledUtil.AUTHOR));
                break;
            case ConstantFiledUtil.ADMIN:
                // 如果是只需要admin的权限，则把admin权限以上的放进去
                authenticationList.add(new SecurityConfig(preString + ConstantFiledUtil.ADMIN));
                authenticationList.add(new SecurityConfig(preString + ConstantFiledUtil.AUTHOR));
                break;
            case ConstantFiledUtil.AUTHOR:
                authenticationList.add(new SecurityConfig(preString + ConstantFiledUtil.AUTHOR));
                break;
        }

        return authenticationList;
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
