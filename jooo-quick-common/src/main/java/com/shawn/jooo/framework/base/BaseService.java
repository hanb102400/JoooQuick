package com.shawn.jooo.framework.base;


import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 基础service接口，参考JPA实现
 *
 * @author shawn
 */
public interface BaseService<T, ID extends Serializable> {

    int count();

    boolean existsById(ID id);

    /**
     * find
     */
    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    List<T> findAllByIds(List<ID> ids);

    Optional<T> findOneById(ID id);

    /**
     * save or update
     */
    void save(T entity);

    void update(T entity);

    void update(T entity, Example example);

    void saveOrUpdate(T entity);

    void saveAllInBatch(List<T> entities);

    /**
     * delete
     */
    void deleteAll();

    void deleteAll(List<ID> ids);

    void deleteById(ID id);

    /**
     * Example
     */
    long count(Example example);

    boolean exists(Example example);

    void delete(Example example);

    List<T> findAll(Example example);

    Page<T> findAll(Example example, Pageable pageable);

    Optional<T> findOne(Example example);
}