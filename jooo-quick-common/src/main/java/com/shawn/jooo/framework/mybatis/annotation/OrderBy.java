package com.shawn.jooo.framework.mybatis.annotation;

import com.shawn.jooo.framework.mybatis.condition.Direction;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface OrderBy {

    Direction direction() default Direction.ASC;

    int site() default 1;
}
