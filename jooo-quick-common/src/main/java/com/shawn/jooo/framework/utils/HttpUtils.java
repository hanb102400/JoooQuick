package com.shawn.jooo.framework.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * Http工具类
 *
 * @author shawn
 */
public class HttpUtils {

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = SpringContextHolder.getBean(RestTemplate.class);
        return restTemplate;
    }

    public static String getJson(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = new RestTemplate().getForEntity(url, String.class, params);
        return resp.getBody();
    }

    public static String postJson(String url, Map<String, Object> params) {
        MultiValueMap valueMap = new LinkedMultiValueMap();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            valueMap.add(entry.getKey(), entry.getValue());
        }
        ResponseEntity<String> resp = getRestTemplate().postForEntity(url, valueMap, String.class);
        return resp.getBody();
    }
}
