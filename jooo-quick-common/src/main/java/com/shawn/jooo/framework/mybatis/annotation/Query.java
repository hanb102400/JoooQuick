package com.shawn.jooo.framework.mybatis.annotation;


import com.shawn.jooo.framework.mybatis.condition.MatchMode;
import com.shawn.jooo.framework.mybatis.condition.QueryMode;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface Query {

    QueryMode query() default QueryMode.EQ;

    MatchMode match() default MatchMode.ANY;
}
