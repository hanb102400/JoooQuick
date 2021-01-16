package com.shawn.jooo.framework.json.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shawn.jooo.framework.json.convert.TimeToJsonDeserializer;
import com.shawn.jooo.framework.json.convert.TimeToJsonSerializer;
import com.shawn.jooo.framework.json.type.TimestampType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = TimeToJsonSerializer.class)
@JsonDeserialize(using = TimeToJsonDeserializer.class)
public @interface Timestamp {

    TimestampType value() default TimestampType.TIMESTAMP;

    String format() default "yyyy-MM-dd HH:mm:ss";
}
