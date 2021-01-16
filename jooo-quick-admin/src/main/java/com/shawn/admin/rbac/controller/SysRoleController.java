package com.shawn.admin.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.admin.rbac.entity.SysRole;
import com.shawn.admin.rbac.service.SysRoleService;


/**
 *
 * 角色信息表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询一条数据
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Long roleId) {
        SysRole sysRole = sysRoleService.findById(roleId).get();
        return Responses.success(sysRole);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysRole
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody(required=false) SysRole sysRole) {
        Page page = sysRoleService.findAll(QueryHelper.getExample(sysRole),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysRole
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysRole
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Long roleId) {
        sysRoleService.deleteById(roleId);
        return Responses.success();
    }

 }














