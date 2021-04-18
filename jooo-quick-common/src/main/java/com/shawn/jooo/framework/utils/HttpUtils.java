package com.shawn.jooo.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * Http工具类
 *
 * @author shawn
 */
public class HttpUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = SpringContextHolder.getBean(RestTemplate.class);
        return restTemplate;
    }

    public static String get(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = new RestTemplate().getForEntity(url, String.class, params);
        return resp.getBody();
    }

    public static String post(String url, Map<String, Object> params) {
        MultiValueMap valueMap = new LinkedMultiValueMap();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            valueMap.add(entry.getKey(), entry.getValue());
        }
        ResponseEntity<String> resp = getRestTemplate().postForEntity(url, valueMap, String.class);
        return resp.getBody();
    }

    public static String postJson(String url, Map<String, Object> params) throws JsonProcessingException {

        HttpHeaders header = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        header.setContentType(mediaType);
        header.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON.toString());

        String json = objectMapper.writeValueAsString(params);
        HttpEntity<String> httpEntity = new HttpEntity<>(json, header);
        ResponseEntity<String> resp = new RestTemplate().postForEntity(url, httpEntity, String.class);
        return resp.getBody();
    }
}
