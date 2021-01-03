package ${@tpl.mapper.packageName};

import org.apache.ibatis.annotations.Mapper;
<#include "global.ftl">
import ${baseMapper};
import ${@tpl.entity.packageName}.${table.className};


/**
 *
 * ${table.remark!}
 *
 * @author ${author!}
 */
@Mapper
public interface ${table.className}Mapper extends BaseMapper<${table.className},<@keyType/>>{

}