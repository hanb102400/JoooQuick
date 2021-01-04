package com.shawn.rbac.controller;


import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Pageable;
import com.shawn.rbac.entity.SysRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysUser;
import com.shawn.rbac.service.SysUserService;


/**
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
        //查询条件
        SysUser sysUser = sysUserService.findOneById(userId).get();
        return Responses.getSuccessResponse(sysUser);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param query
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody(required = false) SysUser query) {

        //查询条件
        Example example = QueryHelper.getExample(query);
        example.<SysUser>and().andNotEqualTo(SysUser::getStatus, -1);
        //分页条件
        Pageable pageable = PageHelper.getPage();
        //查询
        Page<SysUser> page = sysUserService.findAll(example, pageable);
        return Responses.getSuccessResponse(page);
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
        sysUserService.save(sysUser);
        return Responses.getDefaultSuccessResponse();
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
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 删除
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestBody SysUser sysUser) {
        sysUserService.deleteById(sysUser.getUserId());
        return Responses.getDefaultSuccessResponse();
    }

}














