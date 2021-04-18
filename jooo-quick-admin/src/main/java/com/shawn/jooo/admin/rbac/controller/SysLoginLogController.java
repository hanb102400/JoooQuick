package com.shawn.jooo.admin.rbac.controller;


import com.shawn.jooo.framework.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.admin.rbac.entity.SysLoginLog;
import com.shawn.jooo.admin.rbac.service.SysLoginLogService;


/**
 *
 * 系统访问记录
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysLoginLog")
public class SysLoginLogController extends BaseController {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 查询一条数据
     *
     * @param loginId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer loginId) {
        SysLoginLog sysLoginLog = sysLoginLogService.findById(loginId).get();
        return Responses.success(sysLoginLog);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysLoginLog
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(SysLoginLog sysLoginLog) {
        Page page = sysLoginLogService.findAll(QueryHelper.getExample(sysLoginLog),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysLoginLog
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysLoginLog sysLoginLog) {
        sysLoginLogService.save(sysLoginLog);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysLoginLog
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysLoginLog sysLoginLog) {
        sysLoginLogService.update(sysLoginLog);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param loginId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer loginId) {
        sysLoginLogService.deleteById(loginId);
        return Responses.success();
    }

 }














