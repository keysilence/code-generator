package com.silence.code.generator.sm.model;

/**
 * Created by Silence on 2018/3/26.
 */
public class Column {

    private String type;
    private String name;
    private String dbName;
    private String nameUpper;
    private String annotation;
    private String tdColName;
    private Boolean isLast;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setNameUpper(String nameUpper) {
        this.nameUpper = nameUpper;
    }

    public String getNameUpper() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getTdColName() {
        String tdColName = "<td>$!{item." + name + "}</td>";
        return tdColName;
    }

    public void setTdColName(String tdColName) {
        this.tdColName = tdColName;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    @Override
    public String toString() {
        return "Column{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", dbName='" + dbName + '\'' +
                ", nameUpper='" + nameUpper + '\'' +
                ", annotation='" + annotation + '\'' +
                ", tdColName='" + tdColName + '\'' +
                ", isLast=" + isLast +
                '}';
    }
}
