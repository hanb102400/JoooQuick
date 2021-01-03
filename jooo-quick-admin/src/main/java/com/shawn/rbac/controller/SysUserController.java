package com.shawn.rbac.controller;


import com.shawn.jooo.framework.mybatis.annotation.Query;
import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.mybatis.condition.QueryWrapper;
import com.shawn.rbac.vo.SysUserVo;
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
    public Response list(@RequestBody(required = false) SysUserVo query) {
        //包装bean和vo类
        QueryWrapper wrapper = QueryHelper.of(SysUser.class, SysUserVo.class);
        Example example = wrapper.ofExample(query);
        example.<SysUser>and().notEq(SysUser::getStatus,-1);

        //查询
        Page<SysUser> page = sysUserService.findAll(example, PageHelper.getPage());
        //转化vo
        Page<SysUserVo> voPage = wrapper.ofVoPage(page);
        return Responses.getSuccessResponse(voPage);
    }

    /**
     * 保存
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(SysUser sysUser) {
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
     * @param vo
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestBody SysUser vo) {
        sysUserService.deleteById(vo.getUserId());
        return Responses.getDefaultSuccessResponse();
    }

}














