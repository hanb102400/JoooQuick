package com.shawn.admin.auth.service;

import com.shawn.jooo.framework.constant.StatusCode;
import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.admin.rbac.entity.SysUser;
import com.shawn.admin.rbac.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务类，用来加载用户账号，用户密码，用户权限信息。
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //获取用户
        Example example = new Example();
        example.createCriteria(SysUser.class)
                .andEqualTo(SysUser::getUsername, username)
                .andEqualTo(SysUser::getStatus, StatusCode.ENABLE.getCode());
        SysUser sysUser = sysUserService.findOne(example).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在")
        );

        //获取用户权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        //返回用户权限信息
        User userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
        return userDetails;
    }
}