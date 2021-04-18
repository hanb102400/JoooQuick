package com.shawn.jooo.admin.rbac.controller;


import com.shawn.jooo.framework.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.admin.rbac.entity.SysOperateLog;
import com.shawn.jooo.admin.rbac.service.SysOperateLogService;


/**
 *
 * 操作日志记录
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysOperateLog")
public class SysOperateLogController extends BaseController {

    @Autowired
    private SysOperateLogService sysOperateLogService;

    /**
     * 查询一条数据
     *
     * @param operId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer operateId) {
        SysOperateLog sysOperateLog = sysOperateLogService.findById(operateId).get();
        return Responses.success(sysOperateLog);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysOperateLog
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody SysOperateLog sysOperateLog) {
        Page page = sysOperateLogService.findAll(QueryHelper.getExample(sysOperateLog),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysOperateLog
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysOperateLog sysOperateLog) {
        sysOperateLogService.save(sysOperateLog);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysOperateLog
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysOperateLog sysOperateLog) {
        sysOperateLogService.update(sysOperateLog);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param operId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer operId) {
        sysOperateLogService.deleteById(operId);
        return Responses.success();
    }

 }














