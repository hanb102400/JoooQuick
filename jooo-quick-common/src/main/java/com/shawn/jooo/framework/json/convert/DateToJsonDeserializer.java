package com.shawn.jooo.framework.json.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import com.shawn.jooo.framework.json.annotation.JsonDate;
import com.shawn.jooo.framework.json.type.TimestampType;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DateToJsonDeserializer extends JsonDeserializer<Long> implements ContextualDeserializer {


    public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String TIME_PATTERN = "HH:mm:ss";

    public static String DATE_PATTERN = "yyyy-MM-dd";

    private TimestampType type;
    private String format;

    public DateToJsonDeserializer() {
    }

    public DateToJsonDeserializer(TimestampType type, String format) {
        this.type = type;
        this.format = format;
    }

    /**
     * JSON时间，字符串 转 time
     *
     * @param jsonParser
     * @param deserializationContext
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if (text != null) {
            long timestamp = 0L;
            LocalDateTime dateTime = null;
            if (TIMESTAMP_PATTERN.equals(format)) { //解析json时间，毫秒换到毫秒转
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                dateTime = LocalDateTime.parse(text, formatter);
            }
            if (TIME_PATTERN.equals(format)) {//解析json时间，秒转换到毫秒
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalTime time = LocalTime.parse(text, formatter);
                dateTime = LocalDateTime.of(LocalDate.MIN, time);

            }
            if (DATE_PATTERN.equals(format)) { //解析json时间，天转换到毫秒
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate date = LocalDate.parse(text, formatter);
                dateTime = LocalDateTime.of(date, LocalTime.MIN);
            }
            if (dateTime != null) {
                if (TimestampType.TIMESTAMP.equals(type)) {
                    Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
                    timestamp = instant.toEpochMilli();
                }
                if (TimestampType.TIME.equals(type)) {
                    Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
                    timestamp = TimeUnit.SECONDS.convert(instant.toEpochMilli(), TimeUnit.MILLISECONDS);
                }
                if (TimestampType.DATE.equals(type)) {
                    Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
                    timestamp = TimeUnit.DAYS.convert(instant.toEpochMilli(), TimeUnit.MILLISECONDS);
                }
            }
            return timestamp;
        }
        return null;
    }

    @Override
    public JsonDeserializer<Long> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            JsonDate ann = beanProperty.getAnnotation(JsonDate.class);
            if (ann != null) {
                type = ann.value();
                format = ann.format();
                if (StringUtils.isBlank(format)) {
                    if (TimestampType.TIMESTAMP.equals(type)) { //生成json时间，毫秒转换到毫秒
                        format = TIMESTAMP_PATTERN;
                    }
                    if (TimestampType.TIME.equals(type)) {//生成json时间，秒转换到毫秒
                        format = TIME_PATTERN;
                    }
                    if (TimestampType.DATE.equals(type)) { //生成json时间，转换到天
                        format = DATE_PATTERN;
                    }
                }
                return new DateToJsonDeserializer(type, format);
            }
        }
        return null;
    }
}
