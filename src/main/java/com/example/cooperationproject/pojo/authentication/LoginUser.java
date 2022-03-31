package com.example.cooperationproject.pojo.authentication;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.cooperationproject.pojo.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class LoginUser implements Serializable, UserDetails {

    private User user;

    // 存储权限信息
    private List<String> userPermissions;

    public LoginUser(User user, List<String> userPermissions) {
        this.user = user;
        this.userPermissions = userPermissions;
    }

    // 存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private List<GrantedAuthority> userAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.nonNull(userAuthorities)){
            return userAuthorities;
        }

        //把userPermissions中字符串类型的权限信息转换成GrantedAuthority对象存入userAuthorities中
        userAuthorities = userPermissions
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return userAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
