package com.shawn.jooo.framework.mybatis.condition;

import com.shawn.jooo.framework.mybatis.reflect.Fn;
import com.shawn.jooo.framework.mybatis.reflect.FnReflections;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 排序工具
 *
 * @author shawn
 */
public class Sort {

    public static final Direction DESC = Direction.DESC;

    public static final Direction ASC = Direction.ASC;

    private Direction direct;

    private String column;

    private int site = 1;

    public Direction getDirect() {
        return direct;
    }

    public void setDirect(Direction direct) {
        this.direct = direct;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    private Sort(Direction direct, String column) {
        this.direct = direct;
        this.column = column;
    }

    private Sort(Direction direct, String column, int site) {
        this.direct = direct;
        this.column = column;
        this.site = site;
    }

    public static Sort order(Direction direct, String column) {
        return new Sort(direct, column);
    }

    public static <U> Sort order(Direction direct, Fn<U, Object> fn) {
        return new Sort(direct, FnReflections.fnToColumnName(fn));
    }

    public static Sort orderBy(Direction direct, String column, int site) {
        return new Sort(direct, column, site);
    }


    public static <U> Sort orderBy(Direction direct, Fn<U, Object> fn, int site) {
        return new Sort(direct, FnReflections.fnToColumnName(fn), site);
    }

    public static String toOrderBySql(Sort... sorts) {
        List sortList = Arrays.stream(sorts).sorted(Comparator.comparing(Sort::getSite))
                .map(item -> item.column + " " + item.direct.getCode()).collect(Collectors.toList());
        return StringUtils.join(sortList, ",");
    }

}
