package com.shawn.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysDictType;
import com.shawn.rbac.service.SysDictTypeService;


/**
 *
 * 字典类型表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysDictType")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询一条数据
     *
     * @param dictId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer dictId) {
        SysDictType sysDictType = sysDictTypeService.findOneById(dictId).get();
        return Responses.getSuccessResponse(sysDictType);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDictType
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(SysDictType sysDictType) {
        Page page = sysDictTypeService.findAll(QueryHelper.getExample(sysDictType),  PageHelper.getPage());
        return Responses.getSuccessResponse(page);
    }

    /**
     * 保存
     *
     * @param sysDictType
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(SysDictType sysDictType) {
        sysDictTypeService.save(sysDictType);
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 编辑
     *
     * @param dictId
     * @param sysDictType
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestParam Integer dictId, SysDictType sysDictType) {
        sysDictTypeService.update(sysDictType);
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 删除
     *
     * @param dictId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer dictId) {
        sysDictTypeService.deleteById(dictId);
        return Responses.getDefaultSuccessResponse();
    }

 }














