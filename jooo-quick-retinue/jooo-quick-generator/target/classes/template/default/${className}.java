package ${@tpl.entity.packageName};
<#include "global.ftl">
import ${baseBean};

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * ${table.remark!}
 *
 * @author ${author!}
 */
@Entity
@Table(name="${table.tableName}")
public class ${className} extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

<#-- 生成属性 -->
<@generateKeyColumns/>
<@generateNotKeyColumns/>
<#-- 构造方法 -->
<@generateConstructor/>
<#-- 生成getset方法 -->
<@generateJavaSetterGetter/>
}

<#-- entity函数定义=================== -->
<#--=================== 主键 ===================-->
<#macro generateKeyColumns>
<#if table.keyCount == 1 >
    /**
     *  ID: ${table.keyColumn.field}  ${table.keyColumn.remark!}
     */
    @Id
    @Column(name="${table.keyColumn.column}")
    private ${table.keyColumn.javaType} ${table.keyColumn.field};

</#if>
</#macro>

<#--=================== 非主键 ===================-->
<#macro generateNotKeyColumns>
<#list table.notKeyColumns as column>
    /**
     * ${column.field} ${column.remark!}
     */
    @Column(name="${column.column}")
    private ${column.javaType} ${column.field};

</#list>
</#macro>
<#--=================== Constructor方法 =======================-->
<#macro generateConstructor>
    public ${table.className}(){}

</#macro>

<#--============= SetterGetterMethod =============-->
<#macro generateJavaSetterGetter>
<#list table.columns as column >
    public void set${column.field?cap_first}( ${column.javaType} ${column.field} ) {
            this.${column.field} = ${column.field};
    }

    public ${column.javaType} get${column.field?cap_first}() {
        return this.${column.field};
    }

</#list>

</#macro>




