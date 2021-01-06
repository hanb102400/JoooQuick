package com.shawn.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import com.shawn.jooo.framework.base.BaseController;
import com.shawn.rbac.entity.SysDictData;
import com.shawn.rbac.service.SysDictDataService;


/**
 *
 * 字典数据表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysDictData")
public class SysDictDataController extends BaseController {

    @Autowired
    private SysDictDataService sysDictDataService;

    /**
     * 查询一条数据
     *
     * @param dictCode
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer dictCode) {
        SysDictData sysDictData = sysDictDataService.findOneById(dictCode).get();
        return Responses.success(sysDictData);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDictData
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(SysDictData sysDictData) {
        Page page = sysDictDataService.findAll(QueryHelper.getExample(sysDictData),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysDictData
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysDictData sysDictData) {
        sysDictDataService.save(sysDictData);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysDictData
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysDictData sysDictData) {
        sysDictDataService.update(sysDictData);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer dictCode) {
        sysDictDataService.deleteById(dictCode);
        return Responses.success();
    }

 }














