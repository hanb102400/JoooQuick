package com.shawn.admin.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.admin.rbac.entity.SysMenu;
import com.shawn.admin.rbac.service.SysMenuService;


/**
 *
 * 菜单权限表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询一条数据
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer menuId) {
        SysMenu sysMenu = sysMenuService.findOne(menuId).get();
        return Responses.success(sysMenu);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(SysMenu sysMenu) {
        Page page = sysMenuService.findAll(QueryHelper.getExample(sysMenu),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysMenu
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysMenu sysMenu) {
        sysMenuService.update(sysMenu);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer menuId) {
        sysMenuService.delete(menuId);
        return Responses.success();
    }

 }














