package ${@tpl.service.packageName};

<#include "global.ftl">
import ${baseService};
import ${@tpl.entity.packageName}.${table.className};

/**
 *
 * ${table.remark!}
 *
 * @author ${author!}
 */
public interface ${className}Service extends BaseService<${table.className},<@keyType/>> {


}

