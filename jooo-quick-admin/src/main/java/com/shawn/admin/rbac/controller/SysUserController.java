package com.shawn.admin.rbac.controller;


import com.shawn.admin.auth.service.Md5PasswordEncoder;
import com.shawn.admin.utils.UserLoginUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.admin.rbac.entity.SysUser;
import com.shawn.admin.rbac.service.SysUserService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * 用户信息表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private Md5PasswordEncoder md5PasswordEncoder;

    /**
     * 查询一条数据
     *
     * @param userId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Long userId) {
        SysUser sysUser = sysUserService.findOne(userId).get();
        return Responses.success(sysUser);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysUser
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody(required = false)  SysUser sysUser) {
        Page page = sysUserService.findAll(QueryHelper.getExample(sysUser),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysUser sysUser) {
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setUpdateTime(System.currentTimeMillis());
        sysUser.setCreateUser(UserLoginUtils.getUsername());
        sysUser.setUpdateUser(UserLoginUtils.getUsername());
        String pwd = md5PasswordEncoder.encode(sysUser.getPassword());
        sysUser.setStatus(0);
        sysUser.setPassword(pwd);
        sysUserService.save(sysUser);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysUser sysUser) {
        sysUser.setUpdateTime(System.currentTimeMillis());
        sysUser.setUpdateUser(UserLoginUtils.getUsername());
        sysUserService.update(sysUser);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam String userId) {
        List<String> ids = Arrays.asList(StringUtils.split(userId,","));
        List<Long> idList =ids.stream().map(id->Long.parseLong(id)).collect(Collectors.toList());
        sysUserService.logicDeleteAll(idList);
        return Responses.success();
    }

 }














