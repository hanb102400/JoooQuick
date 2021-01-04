package com.shawn.auth.service;

import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.rbac.entity.SysUser;
import com.shawn.rbac.service.SysUserService;
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
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //获取用户属性
        Example example = new Example();
        example.createCriteria(SysUser.class).andEqualTo(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.findOne(example).orElse(null);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        //获取用户权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        //返回用户权限信息
        User userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
        return userDetails;
    }
}