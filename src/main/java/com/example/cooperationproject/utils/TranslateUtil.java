package com.example.cooperationproject.utils;

import com.example.cooperationproject.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class TranslateUtil {

    public static Map<String,Object> UserToMap (User user){
        HashMap<String,Object> hashMap = new HashMap();
        hashMap.put("userName",user.getUserName());
        hashMap.put("userId",user.getUserId());
        hashMap.put("password",user.getPassword());
        hashMap.put("avatar",user.getAvatar());
        hashMap.put("createTime",user.getCreateTime());
        hashMap.put("deleteFlag",user.getDeleteFlag());
        hashMap.put("department",user.getDepartment());
        hashMap.put("firstName",user.getFirstName());
        hashMap.put("lastName",user.getLastName());
        hashMap.put("nickName",user.getNickName());
        hashMap.put("phone",user.getPhone());
        hashMap.put("sex",user.getSex());
        hashMap.put("description",user.getDescription());
        return hashMap;
    }



}
