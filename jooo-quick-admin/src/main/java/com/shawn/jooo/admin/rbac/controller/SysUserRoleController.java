package com.shawn.jooo.admin.rbac.controller;


import com.shawn.jooo.framework.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.admin.rbac.entity.SysUserRole;
import com.shawn.jooo.admin.rbac.service.SysUserRoleService;


/**
 *
 * 用户角色关联表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController extends BaseController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 查询一条数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer id) {
        SysUserRole sysUserRole = sysUserRoleService.findById(id).get();
        return Responses.success(sysUserRole);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysUserRole
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody SysUserRole sysUserRole) {
        Page page = sysUserRoleService.findAll(QueryHelper.getExample(sysUserRole),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysUserRole
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysUserRole sysUserRole) {
        sysUserRoleService.save(sysUserRole);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysUserRole
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysUserRole sysUserRole) {
        sysUserRoleService.update(sysUserRole);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer id) {
        sysUserRoleService.deleteById(id);
        return Responses.success();
    }

 }














