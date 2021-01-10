package com.shawn.jooo.framework.mybatis.condition;

import com.shawn.jooo.framework.mybatis.annotation.LogicDelete;
import com.shawn.jooo.framework.mybatis.annotation.OrderBy;
import com.shawn.jooo.framework.mybatis.annotation.QueryField;
import com.shawn.jooo.framework.mybatis.annotation.QueryForm;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Id;
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
        boolean customQuery = queryClazz.isAnnotationPresent(QueryForm.class);
        Example example = new Example();
        Example.Criteria criteria = example.createCriteria();
        Field[] fields = queryClazz.getDeclaredFields();
        for (Field field : fields) {

            if (BeanReflections.isSerialVersionUID(field)) {
                continue;
            }
            //过滤逻辑删除字段
            if(field.isAnnotationPresent(LogicDelete.class)){
                String columnName = BeanReflections.getColumnName(field);
                Object value = BeanReflections.invokeGetMethod(field.getName(), query);
                LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
                int deleteFlag = logicDelete.value();
                criteria.andNotEqualTo(columnName, deleteFlag);
            }

            if (!customQuery) {
                //默认查询，根据Id和Column注解生成条件
                if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class)) {
                    String columnName = BeanReflections.getColumnName(field);
                    Object value = BeanReflections.invokeGetMethod(field.getName(), query);
                    if (value == null || StringUtils.isBlank(value.toString())) {
                        continue;
                    }
                    criteria.andEqualTo(columnName, value);
                }
            } else {
                //自定义查询，根据QueryField注解生成条件
                if (field.isAnnotationPresent(QueryField.class)) {

                    QueryField q = field.getAnnotation(QueryField.class);
                    Criteria queryMode = q.value();
                    LikeMatch match = q.match();

                    String columnName = BeanReflections.getColumnName(field);
                    Object value = BeanReflections.invokeGetMethod(field.getName(), query);
                    if (value == null || StringUtils.isBlank(value.toString())) {
                        continue;
                    }
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
        return example;
    }

    public static String createSort(Class<?> queryClazz) {
        Field[] beanFiles = queryClazz.getDeclaredFields();
        List<Sort> sorts = new ArrayList();
        for (Field queryField : beanFiles) {
            if (queryField.isAnnotationPresent(OrderBy.class)) {
                OrderBy orderBy = queryField.getAnnotation(OrderBy.class);
                Direction direction = orderBy.direction();
                int index = orderBy.index();
                String columnName = BeanReflections.getColumnName(queryField);
                Sort sort = Sort.orderBy(direction, columnName, index);
                sorts.add(sort);
            }
        }

        if (!CollectionUtils.isEmpty(sorts)) {
            return Sort.toOrderBySql(sorts.toArray(new Sort[sorts.size()]));
        }
        return null;

    }
}
