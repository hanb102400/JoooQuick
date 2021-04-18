package com.shawn.jooo.framework.mybatis.annotation;

import com.shawn.jooo.framework.mybatis.condition.Direction;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 排序注解
 *
 * @author shawn
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface OrderBy {

    Direction value() default Direction.ASC;

    int index() default 0;
}
