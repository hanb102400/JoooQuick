package com.shawn.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysDepart;
import com.shawn.rbac.service.SysDepartService;


/**
 *
 * 部门表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysDepart")
public class SysDepartController extends BaseController {

    @Autowired
    private SysDepartService sysDepartService;

    /**
     * 查询一条数据
     *
     * @param departId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer departId) {
        SysDepart sysDepart = sysDepartService.findOneById(departId).get();
        return Responses.getSuccessResponse(sysDepart);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(SysDepart sysDepart) {
        Page page = sysDepartService.findAll(QueryHelper.getExample(sysDepart),  PageHelper.getPage());
        return Responses.getSuccessResponse(page);
    }

    /**
     * 保存
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(SysDepart sysDepart) {
        sysDepartService.save(sysDepart);
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 编辑
     *
     * @param departId
     * @param sysDepart
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestParam Integer departId, SysDepart sysDepart) {
        sysDepartService.update(sysDepart);
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 删除
     *
     * @param departId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer departId) {
        sysDepartService.deleteById(departId);
        return Responses.getDefaultSuccessResponse();
    }

 }














