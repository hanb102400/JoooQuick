package com.shawn.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysRoleMenu;
import com.shawn.rbac.service.SysRoleMenuService;


/**
 *
 * 角色和菜单关联表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController extends BaseController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 查询一条数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer id) {
        SysRoleMenu sysRoleMenu = sysRoleMenuService.findOneById(id).get();
        return Responses.success(sysRoleMenu);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysRoleMenu
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(SysRoleMenu sysRoleMenu) {
        Page page = sysRoleMenuService.findAll(QueryHelper.getExample(sysRoleMenu),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysRoleMenu
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysRoleMenu sysRoleMenu) {
        sysRoleMenuService.save(sysRoleMenu);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysRoleMenu
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysRoleMenu sysRoleMenu) {
        sysRoleMenuService.update(sysRoleMenu);
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
        sysRoleMenuService.deleteById(id);
        return Responses.success();
    }

 }














