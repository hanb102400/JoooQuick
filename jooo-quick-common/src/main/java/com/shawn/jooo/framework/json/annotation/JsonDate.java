package com.shawn.jooo.framework.json.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shawn.jooo.framework.json.convert.DateToJsonDeserializer;
import com.shawn.jooo.framework.json.convert.DateToJsonSerializer;
import com.shawn.jooo.framework.json.type.TimestampType;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author shawn
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = DateToJsonSerializer.class)
@JsonDeserialize(using = DateToJsonDeserializer.class)
public @interface JsonDate {

    TimestampType value() default TimestampType.TIMESTAMP;

    String format() default "";
}
