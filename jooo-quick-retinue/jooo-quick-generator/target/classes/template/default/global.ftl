<#-- 变量定义=================== -->
<#assign copyright="Copyright (C) 2013 rats">
<#assign author="jooo.gen">

<#assign baseBean= "com.shawn.jooo.framework.base.BaseBean" >
<#assign baseMapper= "com.shawn.jooo.framework.base.BaseMapper" >
<#assign baseService= "com.shawn.jooo.framework.base.BaseService" >
<#assign baseServiceImpl= "com.shawn.jooo.framework.base.BaseServiceImpl" >
<#assign baseController= "com.shawn.jooo.framework.base.BaseController" >

<#assign example= "com.shawn.jooo.framework.mybatis.condition.Example" >
<#assign page= "com.shawn.jooo.framework.page.Page" >
<#assign pageImpl= "com.shawn.jooo.framework.pageImpl" >
<#assign pageable= "com.shawn.jooo.framework.page.Pagable" >

<#-- 主键类型 -->
<#macro keyType>
<#compress>
<#if table.keyCount == 1 >${table.keyColumn.javaType}</#if>
<#if table.keyCount != 1 >${table.compKey.javaType}</#if>
</#compress>
</#macro>


<#-- 主键类型,全名 -->
<#macro keyTypeFull >
<#compress>
<#if table.keyCount == 1 >${table.keyColumn.$javaType}</#if>
<#if table.keyCount != 1 >${@tpl.entity.packageName}.${table.compKey.$javaType}</#if>
</#compress>
</#macro>

<#-- 主键字段 -->
<#macro keyField>
<#compress>
<#if table.keyCount == 1 >${table.keyColumn.field}</#if>
<#if table.keyCount != 1 >${table.compKey.field}</#if>
</#compress>
</#macro>

<#-- 主键类型和字段 -->
<#macro keyTypeAndField>
<#t><#if table.keyCount == 1 >${table.keyColumn.javaType} ${table.keyColumn.field}</#if>
<#t><#if table.keyCount != 1 >${table.compKey.javaType} ${table.compKey.field}</#if>
</#macro>


<#--<#if table.pkCount != 1 >
    <#list table.pkColumns as column>${column.javaType} ${column.field}<#if column_has_next>,</#if></#list>
</#if>-->


