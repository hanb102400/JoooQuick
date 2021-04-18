package com.shawn.jooo.admin.rbac.controller;


import com.shawn.jooo.admin.auth.service.Md5PasswordEncoder;
import com.shawn.jooo.admin.utils.UserUtils;
import com.shawn.jooo.framework.base.BaseController;
import com.shawn.jooo.framework.core.request.Requests;
import com.shawn.jooo.framework.core.validate.Validations;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.admin.rbac.entity.SysUser;
import com.shawn.jooo.admin.rbac.service.SysUserService;

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
    public Response detail(@RequestParam Integer userId) {
        SysUser sysUser = sysUserService.findUserById(userId);
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
    public Response list(@RequestBody SysUser sysUser) {
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
        sysUser.setCreateUser(UserUtils.getLoginUsername());
        sysUser.setUpdateUser(UserUtils.getLoginUsername());

        sysUser.setStatus(0);


        sysUserService.insertUser(sysUser);
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
        sysUser.setUpdateUser(UserUtils.getLoginUsername());
        sysUserService.updateUser(sysUser);
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
        List<Integer> idList =ids.stream().map(id->Integer.parseInt(id)).collect(Collectors.toList());
        sysUserService.deleteInBatch(idList);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/resetPass")
    @ResponseBody
    public Response remove(@RequestBody SysUser sysUser) {
        Validations.checkArgument(sysUser.getUserId()!=null,"用户id不能为空");
        Validations.checkArgument(sysUser.getPassword()!=null,"用户密码不能为空");
        SysUser update  = sysUserService.findUserById(sysUser.getUserId());
        String pwd = md5PasswordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(pwd);
        sysUserService.updateUser(update);
        return Responses.success();
    }


 }














