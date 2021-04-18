package com.shawn.jooo.framework.log.annotation;

import java.lang.annotation.*;

/**
 * @author shawn
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
