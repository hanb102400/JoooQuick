package com.shawn.jooo.framework.mybatis.condition;

import com.shawn.jooo.framework.mybatis.annotation.OrderBy;
import com.shawn.jooo.framework.mybatis.annotation.QueryItem;
import com.shawn.jooo.framework.mybatis.annotation.QueryForm;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ExampleHelper工具类
 *
 * @author shawn
 */
public class QueryHelper {

    private static final Logger logger = LoggerFactory.getLogger(QueryHelper.class);


    public static <E> Example getExample(E query) {
        if (query == null) {
            return new Example();
        }
        Example example = createExample(query);
        String orderBy = createSort(query.getClass());
        example.setOrderByClause(orderBy);
        return example;
    }

    private static <E> Example createExample(E query) {

        Class<?> queryClazz = query.getClass();
        boolean custom = queryClazz.isAnnotationPresent(QueryForm.class);
        Example example = new Example();
        Example.Criteria criteria = example.createCriteria();
        Field[] queryFiles = queryClazz.getDeclaredFields();
        for (Field queryField : queryFiles) {

            if (BeanReflections.isSerialVersionUID(queryField)) {
                continue;
            }
            String columnName = BeanReflections.getColumnName(queryField);
            Object value = BeanReflections.invokeGetMethod(queryField, query);
            if (!custom) {
                if (value != null) {
                    criteria.andEqualTo(columnName, value);
                }
            } else {
                if (queryField.isAnnotationPresent(QueryItem.class)) {

                    QueryItem q = queryField.getAnnotation(QueryItem.class);
                    ExpMode queryMode = q.value();
                    Match match = q.match();

                    if (value != null) {
                        switch (queryMode) {
                            case EQ:
                                criteria.andEqualTo(columnName, value);
                                break;
                            case NOT_EQ:
                                criteria.andNotEqualTo(columnName, value);
                                break;
                            case GT:
                                criteria.andGreaterThan(columnName, value);
                                break;
                            case GT_OR_EQ:
                                criteria.andGreaterThanOrEqualTo(columnName, value);
                                break;
                            case LT:
                                criteria.andLessThan(columnName, value);
                                break;
                            case LT_OR_EQ:
                                criteria.andLessThanOrEqualTo(columnName, value);
                                break;
                            case LIKE:
                                criteria.andLike(columnName, match.contact(value.toString()));
                                break;
                            case NOT_LIKE:
                                criteria.andNotLike(columnName, match.contact(value.toString()));
                                break;
                        }
                    }
                }
            }
        }
        return example;
    }

    public static String createSort(Class<?> queryClazz) {
        Field[] beanFiles = queryClazz.getDeclaredFields();
        List<Sort> sorts = new ArrayList();
        for (Field queryField : beanFiles) {
            if (queryField.isAnnotationPresent(OrderBy.class)) {

                OrderBy orderBy = queryField.getAnnotation(OrderBy.class);

                Direction direction = orderBy.direction();
                int site = orderBy.site();

                String columnName = BeanReflections.getColumnName(queryField);
                Sort sort = Sort.orderBy(direction, columnName, site);
                sorts.add(sort);
            }
        }

        if (!CollectionUtils.isEmpty(sorts)) {
            return Sort.toOrderBySql(sorts.toArray(new Sort[sorts.size()]));
        }
        return null;

    }
}
