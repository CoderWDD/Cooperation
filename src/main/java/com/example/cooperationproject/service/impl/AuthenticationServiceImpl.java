package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.mapper.AuthenticationMapper;
import com.example.cooperationproject.pojo.Authentication;
import com.example.cooperationproject.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl extends ServiceImpl<AuthenticationMapper, Authentication> implements AuthenticationService {
    @Override
    public Authentication GetAuthenticationNameByAuId(Integer auId) {
        QueryWrapper<Authentication> wrapper = new QueryWrapper<>();
        wrapper.eq("au_id",auId);
        return getOne(wrapper);
    }
}
