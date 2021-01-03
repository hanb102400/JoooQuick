package com.shawn.jooo.generator.table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shawn
 */
public class CompKey {

    /**
     * 主键列表
     */
    private String javaType;

    private String $javaType;

    private String field;

    public List<Column> columns = new ArrayList<Column>();

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String get$javaType() {
        return $javaType;
    }

    public void set$javaType(String $javaType) {
        this.$javaType = $javaType;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
