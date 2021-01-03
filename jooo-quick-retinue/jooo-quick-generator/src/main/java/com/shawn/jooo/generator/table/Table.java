package com.shawn.jooo.generator.table;

import com.shawn.jooo.generator.config.TableConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shawn
 */
public class Table extends TableConfiguration {

    protected String remark;
    protected String tableType;
    protected String tableNameAlias;
    protected int keyCount;
    protected String fullName;
    private Column keyColumn;
    private CompKey compKey;
    public List<Column> columns = new ArrayList<Column>();
    public List<Column> notKeyColumns = new ArrayList<Column>();

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableNameAlias() {
        return tableNameAlias;
    }

    public void setTableNameAlias(String tableNameAlias) {
        this.tableNameAlias = tableNameAlias;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Column getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(Column keyColumn) {
        this.keyColumn = keyColumn;
    }

    public CompKey getCompKey() {
        return compKey;
    }

    public void setCompKey(CompKey compKey) {
        this.compKey = compKey;
    }

    public List<Column> getNotKeyColumns() {
        return notKeyColumns;
    }

    public void setNotKeyColumns(List<Column> notKeyColumns) {
        this.notKeyColumns = notKeyColumns;
    }
}