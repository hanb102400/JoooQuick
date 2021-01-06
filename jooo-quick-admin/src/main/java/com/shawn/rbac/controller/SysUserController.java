package com.shawn.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysUser;
import com.shawn.rbac.service.SysUserService;


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

    /**
     * 查询一条数据
     *
     * @param userId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Long userId) {
        SysUser sysUser = sysUserService.findOneById(userId).get();
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
    public Response list(SysUser sysUser) {
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
    public Response remove(@RequestParam Long userId) {
        sysUserService.deleteById(userId);
        return Responses.success();
    }

 }














