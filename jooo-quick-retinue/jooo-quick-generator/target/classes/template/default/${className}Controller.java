package ${ @tpl.controller.packageName};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<#include "global.ftl">
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageHelper;
import com.shawn.jooo.framework.response.Response;
import com.shawn.jooo.framework.response.Responses;

import ${baseController};
import ${@tpl.entity.packageName}.${className};
import ${@tpl.service.packageName}.${className}Service;


/**
 *
 * ${table.remark!}
 *
 * @author ${author!}
 */
@RestController
@RequestMapping("/${className?uncap_first}")
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

    /**
     * 查询一条数据
     *
     * @param <@keyField/>
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam <@keyTypeAndField/>) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.findOneById(<@keyField/>).get();
        return Responses.getSuccessResponse(${className?uncap_first});
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param ${className?uncap_first}
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(${className} ${className?uncap_first}) {
        Page page = ${className?uncap_first}Service.findAll(QueryHelper.getExample(${className?uncap_first}),  PageHelper.getPage());
        return Responses.getSuccessResponse(page);
    }

    /**
     * 保存
     *
     * @param ${className?uncap_first}
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.save(${className?uncap_first});
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 编辑
     *
     * @param <@keyField/>
     * @param ${className?uncap_first}
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestParam <@keyTypeAndField/>, ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.update(${className?uncap_first});
        return Responses.getDefaultSuccessResponse();
    }

    /**
     * 删除
     *
     * @param <@keyField/>
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam <@keyTypeAndField/>) {
        ${className?uncap_first}Service.deleteById(<@keyField/>);
        return Responses.getDefaultSuccessResponse();
    }

 }














