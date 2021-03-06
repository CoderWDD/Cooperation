package com.example.cooperationproject.manager;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     * @param authentication 包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities。
     * @param object         就是FilterInvocation对象，可以得到request等web资源
     * @param configAttributes configAttributes是本次访问需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null || configAttributes.size() <= 0){
            // 如果没有传权限列表过来，则直接往下走，后面的filter会处理的
            return;
        }

        String roleNeed;

        for (ConfigAttribute configAttribute : configAttributes){
            roleNeed = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()){
                if (roleNeed.toLowerCase().equals(grantedAuthority.toString().toLowerCase())){
                    // 如果有相应的权限，则往下传
                    return ;
                }
            }
        }
        // 如果走到这里了。说明需要权限，但是没有，直接抛异常即可
        throw new AccessDeniedException("权限不够！");
    }

    /**
     * 表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
     * @param attribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
