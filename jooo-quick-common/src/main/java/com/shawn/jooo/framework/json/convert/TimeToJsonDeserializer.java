package com.shawn.jooo.framework.json.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.shawn.jooo.framework.json.annotation.Timestamp;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeToJsonDeserializer extends JsonDeserializer<Long> implements ContextualDeserializer {

    private TimeUnit unit;
    private String format;

    public TimeToJsonDeserializer() {
    }

    public TimeToJsonDeserializer(TimeUnit unit, String format) {
        this.unit = unit;
        this.format = format;
    }

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if (text != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime dateTime = LocalDateTime.parse(text, formatter);
            Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
            long timestamp = unit.convert(instant.toEpochMilli(), unit);
            return timestamp;
        }
        return null;
    }

    @Override
    public JsonDeserializer<Long> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            Timestamp ann = beanProperty.getAnnotation(Timestamp.class);
            if (ann != null) {
                unit = ann.unit();
                format = ann.format();
                return new TimeToJsonDeserializer(unit, format);
            }
        }
        return null;
    }
}
