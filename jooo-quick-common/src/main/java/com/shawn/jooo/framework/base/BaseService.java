package com.shawn.jooo.framework.base;


import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 基础service接口，参考JPA实现
 *
 * @author shawn
 */
public interface BaseService<T, ID extends Serializable> {

    /**
     * count & exists
     */
    long count(Example example);

    long count();

    boolean exists(Example example);

    boolean existsById(ID id);

    /**
     * find
     */
    Optional<T> findById(ID id);

    Optional<T> findOne(Example example);

    T getOne(ID id);

    List<T> findAllById(List<ID> ids);

    List<T> findAll();

    List<T> findAll(Example example);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Example example, Pageable pageable);

    /**
     * insert or update
     */
    //save方法，存在id则update，不存在id执行insert
    T save(T entity);

    void saveAllInBatch(List<T> entities);

    void saveAllInBatch(List<T> entities, int batchSize);

    int insert(T entity);

    int update(T entity);

    int update(T entity, Example example);


    /**
     * delete
     */
    void deleteById(ID id);

    void delete(Example example);

    void deleteAllInBatch();

    void deleteInBatch(List<ID> ids);


}