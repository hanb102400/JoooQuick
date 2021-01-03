package com.shawn.jooo.framework.mybatis.condition;

import com.shawn.jooo.framework.mybatis.annotation.OrderBy;
import com.shawn.jooo.framework.mybatis.annotation.Query;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import com.shawn.jooo.framework.page.Page;
import com.shawn.jooo.framework.page.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
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

    public static QueryWrapper of(Class<?> poClazz, Class<?> voClazz) {
        return new QueryWrapper(poClazz, voClazz);
    }

    public static <VO> Example getExample(QueryVo<VO> query) {
        if (query == null) {
            return new Example();
        }
        return getExample(query, query.getGenericClass());
    }

    public static <PO> Example getExample(PO entity) {
        if (entity == null) {
            return new Example();
        }
        return getExample(entity, entity.getClass());
    }

    public static <VO> Example getExample(VO query, Class<?> entityClazz) {
        if (query == null) {
            return new Example();
        }
        Example example = createExample(query, query.getClass(), entityClazz);
        String orderBy = createSort(query.getClass(), entityClazz);
        example.setOrderByClause(orderBy);
        return example;
    }

    public static <VO> Page<VO> getVoPage(Page page, Class<?> voClazz) {
        List<VO> newList = new ArrayList();
        if (!CollectionUtils.isEmpty(page.getContent())) {
            List list = page.getContent();
            for (Object obj : list) {
                try {
                    VO vo = (VO) voClazz.newInstance();
                    BeanUtils.copyProperties(obj, vo);
                    newList.add(vo);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return new PageImpl(newList, page.getPageNo(), page.getPageSize(), page.getTotalCount());
    }

    private static <VO> Example createExample(VO query, Class<?> voClazz, Class<?> entityClazz) {
        Example example = new Example();
        Example.Criteria criteria = example.createCriteria();

        Field[] beanFiles = voClazz.getDeclaredFields();
        for (Field queryField : beanFiles) {

            if (queryField.isAnnotationPresent(Query.class)) {
                String fieldName = BeanReflections.getFiledName(queryField);
                Object value;
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, voClazz);
                    Method getMethod = pd.getReadMethod();
                    value = getMethod.invoke(query);
                } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                    logger.warn("query field get method not found {}", fieldName);
                    continue;
                }
                Query q = queryField.getAnnotation(Query.class);
                QueryMode queryMode = q.query();
                MatchMode match = q.match();

                try {
                    Field field = entityClazz.getDeclaredField(queryField.getName());
                    String columnName = BeanReflections.getColumnName(field);
                    if (value != null) {
                        switch (queryMode) {
                            case EQ:
                                criteria.eq(columnName, value);
                                break;
                            case NOT_EQ:
                                criteria.notEq(columnName, value);
                                break;
                            case GT:
                                criteria.gt(columnName, value);
                                break;
                            case GT_OR_EQ:
                                criteria.gtOrEq(columnName, value);
                                break;
                            case LT:
                                criteria.lt(columnName, value);
                                break;
                            case LT_OR_EQ:
                                criteria.ltOrEq(columnName, value);
                                break;
                            case LIKE:
                                criteria.like(columnName, match.contact(value.toString()));
                                break;
                            case NOT_LIKE:
                                criteria.notLike(columnName, match.contact(value.toString()));
                                break;
                        }

                    }
                } catch (NoSuchFieldException e) {
                    logger.warn("Example: warn {} has no method {}", entityClazz.getName(), queryField.getName());
                }
            }
        }
        return example;
    }

    public static String createSort(Class<?> voClazz, Class<?> entityClazz) {

        Field[] beanFiles = voClazz.getDeclaredFields();
        List<Sort> sorts = new ArrayList();
        for (Field queryField : beanFiles) {
            if (queryField.isAnnotationPresent(OrderBy.class)) {

                OrderBy ob = queryField.getAnnotation(OrderBy.class);
                Direction direction = ob.direction();
                int site = ob.site();

                try {
                    Field field = entityClazz.getDeclaredField(queryField.getName());
                    String columnName = BeanReflections.getColumnName(field);
                    Sort sort = Sort.orderBy(direction, columnName, site);
                    sorts.add(sort);
                } catch (NoSuchFieldException e) {
                    logger.warn("Example: warn {} has no method {}", entityClazz.getName(), queryField.getName());
                }

            }
        }

        if (sorts.size() > 0) {
            return Sort.toOrderBySql(sorts.toArray(new Sort[sorts.size()]));
        }
        return null;

    }
}
