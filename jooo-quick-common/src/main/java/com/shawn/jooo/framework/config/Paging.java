package com.shawn.jooo.framework.config;

import com.shawn.jooo.framework.utils.SpringContextHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paging")
public class Paging {

    //@Value("${paging.pageNoKey:pageNo}")
    private String pageNoKey = "pageNo";

    //@Value("${paging.pageSizeKey:pageSize}")
    private String pageSizeKey = "pageSize";


    //@Value("${paging.pageNoValue:1}")
    private Integer pageNoValue = 1;

    //@Value("${paging.pageSizeValue:20}")
    private Integer pageSizeValue = 20;


    public String getPageNoKey() {
        return pageNoKey;
    }

    public void setPageNoKey(String pageNoKey) {
        this.pageNoKey = pageNoKey;
    }

    public String getPageSizeKey() {
        return pageSizeKey;
    }

    public void setPageSizeKey(String pageSizeKey) {
        this.pageSizeKey = pageSizeKey;
    }

    public Integer getPageNoValue() {
        return pageNoValue;
    }

    public void setPageNoValue(Integer pageNoValue) {
        this.pageNoValue = pageNoValue;
    }

    public Integer getPageSizeValue() {
        return pageSizeValue;
    }

    public void setPageSizeValue(Integer pageSizeValue) {
        this.pageSizeValue = pageSizeValue;
    }

    public static String pageNoKey() {
        Paging paging = SpringContextHolder.getBean(Paging.class);
        return paging.getPageNoKey();
    }

    public static String pageSizeKey() {
        Paging paging = SpringContextHolder.getBean(Paging.class);
        return paging.getPageSizeKey();
    }

    public static Integer pageNoValue() {
        Paging paging = SpringContextHolder.getBean(Paging.class);
        return paging.getPageNoValue();
    }

    public static Integer pageSizeValue() {
        Paging paging = SpringContextHolder.getBean(Paging.class);
        return paging.getPageSizeValue();
    }

}
