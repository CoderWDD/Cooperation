package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.Authentication;

public interface AuthenticationService extends IService<Authentication> {
    Authentication GetAuthenticationNameByAuId(Integer auId);

}
