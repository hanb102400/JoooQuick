package com.shawn.jooo.framework.json.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.shawn.jooo.framework.json.annotation.Timestamp;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeToJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private TimeUnit unit;
    private String format;

    public TimeToJsonSerializer() {
    }

    public TimeToJsonSerializer(TimeUnit unit, String format) {
        this.unit = unit;
        this.format = format;
    }

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
       if(o instanceof Long || o instanceof Integer) {
           Long timestamp = (Long)o;
           timestamp = unit.toMillis(timestamp);
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
                unit = ann.unit();
                format = ann.format();
                return new TimeToJsonSerializer(unit, format);
            }
        }
        return null;
    }
}
