package com.shawn.jooo.framework.mybatis.annotation;



import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogicDelete {

    int value() default 1;
}
