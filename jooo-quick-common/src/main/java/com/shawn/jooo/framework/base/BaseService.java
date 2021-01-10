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

    boolean exists(ID id);

    /**
     * find
     */
    Optional<T> findOne(ID id);

    Optional<T> findOne(Example example);

    List<T> findAll();

    List<T> findAll(Example example);

    List<T> findAll(List<ID> ids);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Example example, Pageable pageable);

    /**
     * save or update
     */
    int save(T entity);

    int update(T entity);

    int update(T entity, Example example);

    int saveOrUpdate(T entity);

    void saveAllBatch(List<T> entities);

    /**
     * delete
     */
    void deleteAll();

    void deleteAll(List<ID> ids);

    void delete(Example example);

    void delete(ID id);

    /**
     * logic delete
     */
    void logicDeleteAll();

    void logicDeleteAll(List<ID> ids);

    void logicDelete(ID id);

    void logicDelete(Example example);

}