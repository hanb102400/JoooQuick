package com.shawn.jooo.framework.json.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import com.shawn.jooo.framework.json.annotation.JsonDate;
import com.shawn.jooo.framework.json.type.TimestampType;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DateToJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String TIME_PATTERN = "HH:mm:ss";

    public static String DATE_PATTERN = "yyyy-MM-dd";

    private TimestampType type;

    private String format;

    public DateToJsonSerializer() {
    }

    public DateToJsonSerializer(TimestampType type, String format) {
        this.type = type;
        this.format = format;
    }

    /**
     * JSON时间， time 转 字符串
     *
     * @param o
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o instanceof Long || o instanceof Integer) {
            Long timestamp = (Long) o;
            if (TimestampType.TIMESTAMP.equals(type)) { //生成json时间，毫秒转换到毫秒
                timestamp = TimeUnit.MILLISECONDS.convert(timestamp, TimeUnit.MILLISECONDS);
            }
            if (TimestampType.TIME.equals(type)) {//生成json时间，秒转换到毫秒
                timestamp = TimeUnit.MILLISECONDS.convert(timestamp, TimeUnit.SECONDS);
            }
            if (TimestampType.DATE.equals(type)) { //生成json时间，转换到天
                timestamp = TimeUnit.MILLISECONDS.convert(timestamp, TimeUnit.DAYS);
            }
            Instant instant = Instant.ofEpochMilli(timestamp);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String f = formatter.format(dateTime);
            jsonGenerator.writeString(f);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
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
                return new DateToJsonSerializer(type, format);
            }
        }
        return null;
    }
}
