package com.shawn.jooo.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 告警
 * <p>
 * 类型：
 * 1. 短信 2.邮件，3：企业微信机器人
 * 配置：
 * app.alarm.webhook: 微信机器人webhook地址
 *
 */
public class AlarmUtils {

    private static Logger logger = LoggerFactory.getLogger(AlarmUtils.class);

    public static String APP_ALARM_WEBHOOK_CONFIG = "app.alarm.webhook";

    public static Environment getEnv() {
        return SpringContextHolder.getBean(Environment.class);
    }

    public static void notify(AlarmType type,String content) {

        switch (type) {
            //微信方式发送告警消息
            case WEIXIN: {
                String webhook = getEnv().getProperty(APP_ALARM_WEBHOOK_CONFIG);
                Assert.hasText(webhook, "webhook配置不能为空");

                try {
                    Map text = new HashMap();
                    text.put("content", content);
                    Map msg = new HashMap<>();
                    msg.put("msgtype", "text");
                    msg.put("text", text);

                    HttpUtils.postJson(webhook, msg);
                } catch (JsonProcessingException e) {
                    logger.error("json parse ex",e);
                }
                break;
            }
        }

    }

    public enum AlarmType {
        SMS, EMAIL, WEIXIN
    }
}
