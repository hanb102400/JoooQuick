package com.shawn.jooo.module.dict.controller;


import com.shawn.jooo.framework.base.BaseController;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.module.dict.entity.SysDictItem;
import com.shawn.jooo.module.dict.service.SysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysDictItem")
public class SysDictItemController extends BaseController {

    @Autowired
    private SysDictItemService sysDictItemService;

    /**
     * 查询一条数据
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer itemId) {
        SysDictItem sysDictItem = sysDictItemService.findById(itemId).get();
        return Responses.success(sysDictItem);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDictItem
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody SysDictItem sysDictItem) {
        Page page = sysDictItemService.findAll(QueryHelper.getExample(sysDictItem),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysDictItem
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody  SysDictItem sysDictItem) {
        sysDictItemService.save(sysDictItem);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysDictItem
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysDictItem sysDictItem) {
        sysDictItemService.update(sysDictItem);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer itemId) {
        sysDictItemService.deleteById(itemId);
        return Responses.success();
    }

 }














