package com.shawn.jooo.framework.mybatis.mapper;

import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.core.page.Pageable;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

/**
 * Mapper接口
 * <p>
 * 方法描述参照mybatis generator
 *
 * @author shawn
 */
public interface MapperAdapt<T, ID extends Serializable> {

    @SelectProvider(type = MybatisProvider.CountByExampleProvider.class, method = "staticSQL")
    int countByExample(Example example);

    @DeleteProvider(type = MybatisProvider.DeleteByExampleProvider.class, method = "staticSQL")
    int deleteByExample(Example example);

    @DeleteProvider(type = MybatisProvider.DeleteByPrimaryKeyProvider.class, method = "staticSQL")
    int deleteByPrimaryKey(ID id);

    @InsertProvider(type = MybatisProvider.InsertProvider.class, method = "staticSQL")
    int insert(T record);

    @InsertProvider(type = MybatisProvider.InsertSelectiveProvider.class, method = "staticSQL")
    int insertSelective(T record);

    @SelectProvider(type = MybatisProvider.SelectByExampleProvider.class, method = "staticSQL")
    List<T> selectByExample(Example example);

    @SelectProvider(type = MybatisProvider.selectPageByExampleProvider.class, method = "staticSQL")
    List<T> selectPageByExample(@Param("example") Example example, @Param("pageable") Pageable pageable);

    @SelectProvider(type = MybatisProvider.SelectByPrimaryKeyProvider.class, method = "staticSQL")
    T selectByPrimaryKey(ID id);

    @UpdateProvider(type = MybatisProvider.UpdateByExampleSelectiveProvider.class, method = "staticSQL")
    int updateByExampleSelective(@Param("record") T record, @Param("example") Example example);

    @UpdateProvider(type = MybatisProvider.UpdateByExampleProvider.class, method = "staticSQL")
    int updateByExample(@Param("record") T record, @Param("example") Example example);

    @UpdateProvider(type = MybatisProvider.UpdateByPrimaryKeySelectiveProvider.class, method = "staticSQL")
    int updateByPrimaryKeySelective(T record);

    @UpdateProvider(type = MybatisProvider.UpdateByPrimaryKeyProvider.class, method = "staticSQL")
    int updateByPrimaryKey(T record);

    @InsertProvider(type = MybatisProvider.InsertBatchProvider.class, method = "staticSQL")
    int insertBatch(List<T> list);
}
