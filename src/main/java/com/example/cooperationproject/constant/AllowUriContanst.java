package com.example.cooperationproject.constant;

import org.springframework.stereotype.Component;

import java.util.List;

// 鉴权白名单

public class AllowUriContanst {



    public static List<String> uriList;

    static {
        uriList.add("/user/login");
        uriList.add("/user/register");
    }


    public static List<String> getUriList() {
        return uriList;
    }
}
