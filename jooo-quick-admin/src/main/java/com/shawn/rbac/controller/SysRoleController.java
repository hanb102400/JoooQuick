package com.shawn.rbac.controller;


import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Pageable;
import com.shawn.jooo.framework.request.Requests;
import com.shawn.rbac.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysRole;
import com.shawn.rbac.service.SysRoleService;


/**
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

        SysRole sysRole = sysRoleService.findOneById(roleId).get();
        return Responses.getSuccessResponse(sysRole);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param query
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody(required = false) SysRole query) {
        //查询条件
        Example example = QueryHelper.getExample(query);
        example.<SysUser>and().andNotEqualTo(SysUser::getStatus, -1);
        //分页条件
        Pageable pageable = PageHelper.getPage();
        //查询
        Page<SysRole> page = sysRoleService.findAll(example, pageable);
        return Responses.getSuccessResponse(page);
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
        sysRole.setCreateTime(System.currentTimeMillis());
        sysRoleService.save(sysRole);
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 编辑
     *
     * @param sysRole
     * @param sysRole
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysRole sysRole) {
        SysRole update = sysRoleService.findOneById(sysRole.getRoleId()).get();
        BeanUtils.copyProperties(sysRole,update);
        update.setUpdateTime(System.currentTimeMillis());
        sysRoleService.update(update);
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 删除
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam SysRole sysRole) {
        Requests.checkNotNull(sysRole.getRoleId());
        sysRoleService.deleteById(sysRole.getRoleId());
        return Responses.getDefaultSuccessResponse();
    }

}














