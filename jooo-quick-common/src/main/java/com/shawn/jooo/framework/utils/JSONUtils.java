package com.shawn.jooo.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Jackson工具类.
 *
 * @author shawn
 */
public class JSONUtils {

    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String TIME_PATTERN = "HH:mm:ss";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {

        registerTimeModule();

        // 忽略多余属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止使用枚举序号反序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    }


    public static void registerTimeModule(){

        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_PATTERN));

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        objectMapper.registerModule(javaTimeModule);


    }

    /**
     * @param
     * @return
     */
    public static ObjectMapper mapper() {
        return objectMapper;

    }

    /**
     * @param
     * @return
     */
    public static ObjectReader reader() {
        return objectMapper.reader();

    }


    /**
     * @param
     * @return
     */
    public static ObjectWriter writer() {
        return objectMapper.writer();
    }


    /**
     * 将对象转化为Json格式字符串
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String toJSONString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 将Json格式字符串转化为对象
     *
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <T> T parseObject(String json, Class<T> requiredType) {
        try {

            return (T) objectMapper.readValue(json, requiredType);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> T parseObject(String json, Type type) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return (T) objectMapper.readValue(json, javaType);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> valueTypeRef) {
        try {
            return (T) objectMapper.readValue(json, valueTypeRef);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 将Json格式字符串转化为对象列表
     *
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> requiredType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, requiredType);
            return (List<T>) objectMapper.readValue(json, javaType);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> List<T> parseArray(String json, Type type) {
        try {
            JavaType valueType = objectMapper.getTypeFactory().constructType(type);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
            return (List<T>) objectMapper.readValue(json, javaType);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> List<T> parseArray(String json, TypeReference<T> valueTypeRef) {
        try {
            return (List<T>) objectMapper.readValue(json, valueTypeRef);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    /**
     * 读取JSON节点
     *
     * @param json
     * @return
     */
    public static JsonNode readTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 读取JSON节点
     *
     * @param n
     * @param valueType
     * @return
     */
    public static <T> T readNode(JsonNode n, Class<T> valueType) {
        try {
            return objectMapper.treeToValue(n, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());

        }
    }


    /**
     * 将json对象转化为类型对象
     *
     * @param fromValue
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convert(Object fromValue, TypeReference type) {
        try {
            return (T) objectMapper.convertValue(fromValue, type);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    /**
     * 采用JSON格式输出结果对象
     *
     * @param result 结果对象
     * @throws IOException
     */
    public static void writeJSONResponse(HttpServletResponse response, Object result) {
        PrintWriter writer = null;
        response.setContentType("text/plain; charset=UTF-8");
        try {
            writer = response.getWriter();
            String json = toJSONString(result);
            writer.println(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static String getText(JsonNode node, String name) {
        if (node != null) {
            JsonNode cnode = node.get(name);
            if (cnode != null) {
                return cnode.asText();
            }
        }
        return null;
    }

    public static Boolean getBoolean(JsonNode node, String name) {
        if (node != null) {
            JsonNode cnode = node.get(name);
            if (cnode != null) {
                return cnode.asBoolean();
            }
        }
        return null;
    }

    public static Double getDouble(JsonNode node, String name) {
        if (node != null) {
            JsonNode cnode = node.get(name);
            if (cnode != null) {
                return cnode.asDouble();
            }
        }
        return null;
    }

    public static Integer getInt(JsonNode node, String name) {
        if (node != null) {
            JsonNode cnode = node.get(name);
            if (cnode != null) {
                return cnode.asInt();
            }
        }
        return null;
    }

    public static Long getLong(JsonNode node, String name) {
        if (node != null) {
            JsonNode cnode = node.get(name);
            if (cnode != null) {
                return cnode.asLong();
            }
        }
        return null;
    }
}
