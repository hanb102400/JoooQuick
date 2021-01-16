package com.shawn.jooo.framework.json.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.shawn.jooo.framework.json.annotation.Timestamp;
import com.shawn.jooo.framework.json.type.TimestampType;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeToJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private TimestampType type;
    private String format;

    public TimeToJsonSerializer() {
    }

    public TimeToJsonSerializer(TimestampType type, String format) {
        this.type = type;
        this.format = format;
    }

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
       if(o instanceof Long || o instanceof Integer) {
           Long timestamp = (Long)o;
           if (TimestampType.TIMESTAMP.equals(type)) { //生成json时间，毫秒转换到毫秒
               timestamp =  TimeUnit.MILLISECONDS.convert(timestamp, TimeUnit.MILLISECONDS);
           }
           if (TimestampType.TIME.equals(type)) {//生成json时间，秒转换到毫秒
               timestamp = TimeUnit.MILLISECONDS.convert(timestamp, TimeUnit.SECONDS);
           }
           if (TimestampType.DATE.equals(type)) { //生成json时间，转换到天
               timestamp = TimeUnit.MILLISECONDS.convert(timestamp, TimeUnit.DAYS);
           }
           Instant instant = Instant.ofEpochMilli(timestamp);
           LocalDateTime  dateTime = LocalDateTime.ofInstant(instant , ZoneId.systemDefault());

           DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
           String f = formatter.format(dateTime);
           jsonGenerator.writeString(f);
       }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            Timestamp ann = beanProperty.getAnnotation(Timestamp.class);
            if (ann != null) {
                type = ann.value();
                format = ann.format();
                return new TimeToJsonSerializer(type, format);
            }
        }
        return null;
    }
}
