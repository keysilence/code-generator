package com.silence.code.generator.sm.model;

import java.util.List;

/**
 * Created by Silence on 2018/3/26.
 */
public class Table {

    private String name;
    private List<Column> columns;
    private String columnInserts;
    private String columnSelects;
    private String columnValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getColumnInserts() {

        StringBuffer stringBuffer = new StringBuffer();
        for (Column col: columns) {
            stringBuffer.append(",");
            stringBuffer.append("`");
            stringBuffer.append(col.getDbName());
            stringBuffer.append("`");
        }
        return stringBuffer.substring(1);

    }

    public void setColumnInserts(String columnInserts) {
        this.columnInserts = columnInserts;
    }

    public String getColumnSelects() {

        StringBuffer stringBuffer = new StringBuffer();
        for (Column col: columns) {
            stringBuffer.append(",");
            stringBuffer.append("`");
            stringBuffer.append(col.getDbName());
            stringBuffer.append("`");
            stringBuffer.append(" AS ");
            stringBuffer.append("'");
            stringBuffer.append(col.getName());
            stringBuffer.append("'");
        }
        return stringBuffer.substring(1);

    }

    public void setColumnSelects(String columnSelects) {
        this.columnSelects = columnSelects;
    }

    public String getColumnValues() {

        StringBuffer stringBuffer = new StringBuffer();
        for (Column col: columns) {
            stringBuffer.append(",");
            stringBuffer.append("#{");
            stringBuffer.append(col.getName());
            stringBuffer.append("}");
        }
        return stringBuffer.substring(1);

    }

    public void setColumnValues(String columnValues) {
        this.columnValues = columnValues;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", columnInserts='" + columnInserts + '\'' +
                ", columnSelects='" + columnSelects + '\'' +
                ", columnValues='" + columnValues + '\'' +
                '}';
    }

}
