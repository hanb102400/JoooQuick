package com.shawn.jooo.framework.mybatis.annotation;


import com.shawn.jooo.framework.mybatis.condition.Match;
import com.shawn.jooo.framework.mybatis.condition.ExpMode;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface QueryItem {

    ExpMode value() default ExpMode.EQ;

    Match match() default Match.ANY;
}
