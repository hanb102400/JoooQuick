package com.shawn.jooo.admin.rbac.controller;


import com.shawn.jooo.framework.base.BaseController;
import com.shawn.jooo.framework.core.option.Option;
import com.shawn.jooo.framework.core.option.OptionHelper;
import com.shawn.jooo.framework.mybatis.condition.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.admin.rbac.entity.SysRole;
import com.shawn.jooo.admin.rbac.service.SysRoleService;

import java.util.List;


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
    public Response detail(@RequestParam Integer roleId) {
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
    public Response list(@RequestBody SysRole sysRole) {
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
    public Response remove(@RequestParam Integer roleId) {
        sysRoleService.deleteById(roleId);
        return Responses.success();
    }


    /**
     * 编辑
     *
     * @param
     * @return
     */
    @RequestMapping("/options")
    @ResponseBody
    public Response options() {
        Example example = new Example();
        example.createCriteria().andNotEqualTo(SysRole::getStatus, -1);
        List<SysRole> list = sysRoleService.findAll(example);
        List<Option> options = OptionHelper.listToOption(list,"roleId","roleName");
        return Responses.success(options);
    }

 }














