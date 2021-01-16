package com.shawn.jooo.framework.json.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.shawn.jooo.framework.json.annotation.Timestamp;
import com.shawn.jooo.framework.json.type.TimestampType;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeToJsonDeserializer extends JsonDeserializer<Long> implements ContextualDeserializer {

    private TimestampType type;
    private String format;

    public TimeToJsonDeserializer() {
    }

    public TimeToJsonDeserializer(TimestampType type, String format) {
        this.type = type;
        this.format = format;
    }

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if (text != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime dateTime = LocalDateTime.parse(text, formatter);
            Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
            long timestamp = 0L;
            if (TimestampType.TIMESTAMP.equals(type)) { //解析json时间，毫秒转换到毫秒
                timestamp = instant.toEpochMilli();
            }
            if (TimestampType.TIME.equals(type)) {//解析json时间，毫秒转换到秒
                timestamp = TimeUnit.SECONDS.convert(instant.toEpochMilli(), TimeUnit.MILLISECONDS);
            }
            if (TimestampType.DATE.equals(type)) { //解析json时间，毫秒转换到天
                timestamp = TimeUnit.DAYS.convert(instant.toEpochMilli(), TimeUnit.MILLISECONDS);
            }
            return timestamp;
        }
        return null;
    }

    @Override
    public JsonDeserializer<Long> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            Timestamp ann = beanProperty.getAnnotation(Timestamp.class);
            if (ann != null) {
                type = ann.value();
                format = ann.format();
                return new TimeToJsonDeserializer(type, format);
            }
        }
        return null;
    }
}
