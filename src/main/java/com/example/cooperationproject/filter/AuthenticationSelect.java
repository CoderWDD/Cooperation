package com.example.cooperationproject.filter;

import com.example.cooperationproject.constant.ConstantFiledUtil;


public class AuthenticationSelect {

    /**
     * 判断是否是admin能访问的Uri
     * @param Uri
     * @return
     */
    public static boolean isAdminUri(String Uri){
        if (Uri.indexOf(ConstantFiledUtil.PROJECT) != -1 && Uri.indexOf(ConstantFiledUtil.URI_DELETE) != -1){
            // 如果是要对project进行delete操作，则admin不能进行
            return false;
        }
        return true;
    }

    // TODO : 添加更多的USER权限判断

    /**
     * 判断是否是User可以访问的链接
     * @param Uri
     * @return
     */
    public static boolean isUserUri(String Uri){
        // USER只能对item的状态进行修改
        if (Uri.indexOf(ConstantFiledUtil.ITEM) != -1 && Uri.indexOf(ConstantFiledUtil.URI_SET) != -1){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是Author可以访问的链接
     * @param Uri
     * @return author能访问所有的操作，直接返回true
     */
    public static boolean isAuthorUri(String Uri){
        return true;
    }
}
