package com.shawn.admin.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserLoginUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public  static User getUser(){
        return (User)getAuthentication().getPrincipal();
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public  static String getUsername(){
        return getUser().getUsername();
    }
}
