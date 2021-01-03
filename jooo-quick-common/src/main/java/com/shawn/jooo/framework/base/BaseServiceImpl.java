package com.shawn.jooo.framework.base;


import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.mybatis.reflect.ClassTypeAdapt;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageImpl;
import com.shawn.jooo.framework.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 基础service实现，参考JPA实现
 *
 * @author shawn
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> extends ClassTypeAdapt<T> implements BaseService<T, ID>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    private Class<T> type;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

    }

    public BaseServiceImpl() {
        type = getClassType();
        if (type == null) {
            throw new RuntimeException("继承类没有加泛型!");
        }
    }

    public BaseMapper<T, ID> getMapper() {
        BaseMapper<T, ID> mapper = null;
        String entityName = StringUtils.uncapitalize(type.getSimpleName());
        if (applicationContext.containsBean(entityName + "Mapper")) {
            Object bean = applicationContext.getBean(entityName + "Mapper");
            if (bean != null) {
                mapper = (BaseMapper<T, ID>) bean;
            } else {
                throw new RuntimeException("bean not exist by name:" + entityName + "Mapper");
            }
        } else if (applicationContext.containsBean(entityName + "Dao")) {
            Object bean = applicationContext.getBean(entityName + "Dao");
            if (bean != null) {
                mapper = (BaseMapper<T, ID>) bean;
            } else {
                throw new RuntimeException("bean not exist by name:" + entityName + "Dao");
            }
        } else {
            throw new RuntimeException(entityName + "Mapper or " + entityName + "Dao bean not exist ");
        }
        return mapper;
    }


    /**
     * 查询结果数量
     */
    @Override
    public int count() {
        return this.getMapper().countByExample(null);
    }

    /**
     * 根据id判断是否存在
     *
     * @param id
     * @return
     */
    @Override
    public boolean existsById(ID id) {
        return getMapper().selectByPrimaryKey(id) != null;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        return getMapper().selectByExample(null);
    }

    /**
     * 查询所有，分页
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        List<T> data = getMapper().selectPageByExample(null, pageable);
        return new PageImpl<T>(data, pageable);
    }

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> findAllByIds(List<ID> ids) {
        Stream<ID> stream = StreamSupport.stream(ids.spliterator(), false);
        List<T> list = stream.map(id -> getMapper().selectByPrimaryKey(id)).collect(Collectors.toList());
        return list;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findOneById(ID id) {
        T data = getMapper().selectByPrimaryKey(id);
        return Optional.ofNullable(data);
    }

    /**
     * 插入一条记录
     *
     * @param entity
     */
    @Override
    public void save(T entity) {
        getMapper().insert(entity);
    }

    /**
     * 更新一条记录
     *
     * @param entity
     */
    @Override
    public void update(T entity) {
        getMapper().updateByPrimaryKey(entity);
    }

    /**
     * 根据查询条件，更新一条记录
     *
     * @param entity
     * @param example
     */
    @Override
    public void update(T entity, Example example) {
        getMapper().updateByExample(entity, example);
    }

    /**
     * 存在则更新记录，不存在则保存记录
     *
     * @param entity
     */
    @Override
    public void saveOrUpdate(T entity) {
        ID id = getPrimaryKey(entity);
        Assert.notNull(id, "get id column is null");
        if (id != null) {
            T result = getMapper().selectByPrimaryKey(id);
            if (result != null) {
                update(entity);
            } else {
                save(entity);
            }
        }
    }


    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public void deleteById(ID id) {
        getMapper().deleteByPrimaryKey(id);
    }


    /**
     * 根据条件，返回数据数量
     *
     * @param example
     * @return
     */
    @Override
    public long count(Example example) {
        return getMapper().countByExample(example);
    }

    /**
     * 根据条件，判断是否存在
     *
     * @param example
     * @return
     */
    @Override
    public boolean exists(Example example) {
        return getMapper().countByExample(example) > 0;
    }

    /**
     * 根据条件，查询记录
     *
     * @param example
     * @return
     */
    @Override
    public List<T> findAll(Example example) {
        return getMapper().selectByExample(example);
    }

    /**
     * 根据条件，查询记录，并分页
     *
     * @param example
     * @param pageable
     * @return
     */
    @Override
    public Page<T> findAll(Example example, Pageable pageable) {
        List<T> data = getMapper().selectPageByExample(example, pageable);
        return new PageImpl<T>(data, pageable);
    }

    /**
     * 根据条件，删除一条记录
     *
     * @param example
     */
    @Override
    public void delete(Example example) {
        getMapper().deleteByExample(example);
    }

    /**
     * 根据条件，查询一条记录
     *
     * @param example
     * @return
     */
    @Override
    public Optional<T> findOne(Example example) {
        List<T> list = getMapper().selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() == 1) {
                return Optional.of(list.get(0));
            } else if (list.size() > 1) {
                logger.error("has duplicate items result more than one: {}", list.size());
            }
        }
        return Optional.empty();
    }

    /**
     * 批量保存
     *
     * @param entities
     */
    @Override
    public void saveAllInBatch(List<T> entities) {
        getMapper().insertBatch(entities);
    }


    /**
     * 删除列表数据
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteAll(List<ID> ids) {
        ids.forEach(id -> {
            getMapper().deleteByPrimaryKey(id);
        });
    }

    /**
     * 删除所有
     */
    @Override
    @Deprecated
    public void deleteAll() {
        getMapper().deleteByExample(null);
    }

    /**
     * getPrimaryKey
     *
     * @param entity
     * @return
     */
    private ID getPrimaryKey(T entity) {
        ID idValue = null;
        Field[] fileds = getClassType().getDeclaredFields();
        Optional<Field> optional = Arrays.stream(fileds).filter(field -> field.isAnnotationPresent(Id.class)).findFirst();
        if (optional.isPresent()) {
            Field field = optional.get();
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), getClassType());
                Method getMethod = pd.getReadMethod();
                idValue = (ID) getMethod.invoke(entity);
            } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
                throw new RuntimeException("serviceImpl get PrimaryKey value error", e);
            }
        }
        return idValue;
    }
}
