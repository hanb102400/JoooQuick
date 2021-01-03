package ${@tpl.serviceImpl.packageName};

<#include "global.ftl">
import org.springframework.stereotype.Service;

import ${baseServiceImpl};
import ${@tpl.entity.packageName}.${className};
import ${@tpl.service.packageName}.${className}Service;


/**
 *
 * ${table.remark!}
 *
 * @author ${author!}
 */
@Service("${className?uncap_first}Service")
public class ${className}ServiceImpl extends BaseServiceImpl<${table.className},<@keyType/>> implements ${className}Service {


}



