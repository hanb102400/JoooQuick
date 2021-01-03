package ${@tpl_dao.packageName};

<#include "global.ftl">

import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ${example};
import ${@tpl_entity.packageName}.${className};
import ${@tpl_mapper.packageName}.${className}Mapper;

/**
 *
 * ${table.remark!}
 *
 * @author ${author!}
 */
@Repository
public class ${className}Dao{

    private static final Logger logger = LoggerFactory.getLogger(${className}Dao.class);

    @Resource
    private ${className}Mapper ${className?uncap_first}Mapper;

    /**
     * 根据查询条件，计算${table.tableName}数量
     *
     * @param example 通用查询条件类
     * @return int 结果数量
     */
    public long countByExample(Example example) {
        long count = this.${className?uncap_first}Mapper.countByExample(example);
        return count;
    }

    /**
     * 根据查询条件，删除${table.tableName}记录
     *
     * @param example
     * @return int
     */
    public int deleteByExample(Example example) {
        return this.${className?uncap_first}Mapper.deleteByExample(example);
    }

<#if table.pkCount == 1>
    /**
     * 根据主键，删除${table.tableName}记录
     *
     * @param <@pkField/>
     * @return int
     */
    public int deleteByPrimaryKey(<@pkTypeAndField/>) {
        return this.${className?uncap_first}Mapper.deleteByPrimaryKey(<@pkField/>);
    }

</#if>
    /**
     * 插入一条${table.tableName}记录
     *
     * @param record
     * @return int
     */
    public int insert(${className} record) {
        return this.${className?uncap_first}Mapper.insert(record);
    }

    /**
     * 插入一条${table.tableName}记录
     *
     * @param record
     * @return int
     */
    public int insertSelective(${className} record) {
        return this.${className?uncap_first}Mapper.insertSelective(record);
    }

    /**
     * 根据查询条件类，返回${table.tableName}结果集
     *
     * @param example
     * @return List<${className}>
     */
    public List<${className}> selectByExample(Example example) {
        return this.${className?uncap_first}Mapper.selectByExample(example);
    }

    /**
     * 据查询条件类，返回${table.tableName}分页结果集
     *
     * @param example
     * @param rowBounds
     * @return List<${className}>
     */
    public List<${className}> selectByExample(Example example, RowBounds rowBounds) {
        return this.${className?uncap_first}Mapper.selectByExample(example, rowBounds);
    }

<#if table.pkCount == 1>
    /**
     * 根据主键，查询${table.tableName}结果
     *
     * @param <@pkField/>
     * @return ${className}
     */
    public ${className} selectByPrimaryKey(<@pkTypeAndField/>) {
        return this.${className?uncap_first}Mapper.selectByPrimaryKey(<@pkField/>);
    }

</#if>

    /**
     * 根据查询条件更新${table.tableName}全部字段
     *
     * @param record
     * @param example
     * @return int
     */
    public int updateByExample(${className} record, Example example) {
        return this.${className?uncap_first}Mapper.updateByExample(record, example);
    }

    /**
     * 根据查询条件更新${table.tableName}部分字段，忽略为空字段
     *
     * @param record
     * @param example
     * @return int
     */
    public int updateByExampleSelective(${className} record, Example example) {
        return this.${className?uncap_first}Mapper.updateByExampleSelective(record, example);
    }

<#if table.pkCount == 1>


    /**
     * 根据主键更新${table.tableName}全部字段
     *
     * @param record 要更新成为的${className}对象
     * @return int 更新记录数
     */
    public int updateByPrimaryKey(${className} record) {
        return this.${className?uncap_first}Mapper.updateByPrimaryKey(record);
    }

    /**
     * 根据主键更新${table.tableName}部分字段，忽略为空字段
     *
     * @param record
     * @return int
     */
    public int updateByPrimaryKeySelective(${className} record) {
        return this.${className?uncap_first}Mapper.updateByPrimaryKeySelective(record);
    }

</#if>
}