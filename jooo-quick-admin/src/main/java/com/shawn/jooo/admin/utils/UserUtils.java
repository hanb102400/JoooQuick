package com.shawn.jooo.admin.utils;


import com.shawn.jooo.admin.rbac.entity.SysUser;
import com.shawn.jooo.admin.rbac.service.SysUserService;
import com.shawn.jooo.framework.utils.SpringContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * 用户工具类
 *
 * @author shawn
 */
public class UserUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getLoginAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static User getLoginUser() {
        return (User) getLoginAuthentication().getPrincipal();
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public static String getLoginUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取当前登录用户Id
     *
     * @return
     */
    public static Integer getLoginUserId() {
        String username = getLoginUsername();
        return UserUtils.getUserId(username);
    }

    public static Integer getUserId(String username) {
        SysUserService sysUserService = SpringContextHolder.getBean(SysUserService.class);
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser != null) {
            return sysUser.getUserId();
        }
        return null;
    }


}
