package com.shawn.jooo.framework.mybatis.annotation;


import com.shawn.jooo.framework.mybatis.condition.LikeMatch;
import com.shawn.jooo.framework.mybatis.condition.Criteria;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 *
 * @author shawn
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface QueryField {

    Criteria value() default Criteria.EQ;

    LikeMatch match() default LikeMatch.ANY;
}
