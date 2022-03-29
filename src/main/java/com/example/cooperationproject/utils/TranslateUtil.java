package com.example.cooperationproject.utils;

import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TranslateUtil {

    public static Map<String, Object> UserToMap(User user) {
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("userName", user.getUserName());
        hashMap.put("userId", user.getUserId());
        hashMap.put("password", user.getPassword());
        hashMap.put("avatar", user.getAvatar());
        hashMap.put("createTime", user.getCreateTime());
        hashMap.put("deleteFlag", user.getDeleteFlag());
        hashMap.put("department", user.getDepartment());
        hashMap.put("firstName", user.getFirstName());
        hashMap.put("lastName", user.getLastName());
        hashMap.put("nickName", user.getNickName());
        hashMap.put("phone", user.getPhone());
        hashMap.put("sex", user.getSex());
        hashMap.put("description", user.getDescription());
        return hashMap;
    }

    /**
     * 返回访问当前uri需要的最低权限
     *
     * @param uri
     * @return
     */
    public static String UriToAuthentication(String uri) {
        if (StringUtils.isNullOrEmpty(uri)) return null;

        uri = uri.toLowerCase();

        if (uri.indexOf("user") != -1) {
            // 如果是访问user层的，直接返回
            return null;
        }

        String[] strings = uri.split("/");

        if (Objects.isNull(strings) || strings.length < 3) {
            return null;
        }


        // project delete -> author
        // project add modify set -> admin


        if (ConstantFiledUtil.PROJECT.toLowerCase().equals(strings[0])
                && ConstantFiledUtil.URI_DELETE.toLowerCase().equals(strings[1])) {
            // 只有项目的创建者，才能删除项目
            return ConstantFiledUtil.AUTHOR;
        }


        if (ConstantFiledUtil.PROJECT.toLowerCase().equals(strings[0])
                && (ConstantFiledUtil.URI_MODIFY.toLowerCase().equals(strings[1])
                || ConstantFiledUtil.URI_ADD.toLowerCase().equals(strings[1])
                || ConstantFiledUtil.URI_SET.toLowerCase().equals(strings[1]))) {
            return ConstantFiledUtil.ADMIN;
        }


        // item delete -> author
        // item modify -> admin
        // item set -> user

        if (ConstantFiledUtil.ITEM.toLowerCase().equals(strings[0])
                && ConstantFiledUtil.URI_DELETE.toLowerCase().equals(strings[1])) {
            // 只有任务的创建者，才能删除任务
            return ConstantFiledUtil.AUTHOR;
        }

        if (ConstantFiledUtil.ITEM.toLowerCase().equals(strings[0])
                && (ConstantFiledUtil.URI_MODIFY.toLowerCase().equals(strings[1])
                || ConstantFiledUtil.URI_ADD.toLowerCase().equals(strings[1])
                || ConstantFiledUtil.URI_SET.toLowerCase().equals(strings[1]))) {
            return ConstantFiledUtil.ADMIN;
        }

        // 除了以上需要特殊权限的操作，其他的操作，一般的用户权限就可以访问
        // 直接返回同意的权限
        return ConstantFiledUtil.USER;
    }


}
