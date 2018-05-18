package com.silence.code.generator.sm.util;

/**
 * Created by Silence on 2018/3/26.
 */
public class TypeConvert {

    public static String getType(String dbType) {

        String type = "";

        if (dbType.toUpperCase().equals("VARCHAR")) {
            type = "String";
        } else if (dbType.toUpperCase().equals("CHAR")) {
            type = "String";
        } else if (dbType.toUpperCase().equals("BLOB")) {
            type = "byte[]";
        } else if (dbType.toUpperCase().equals("TEXT")) {
            type = "String";
        } else if (dbType.toUpperCase().equals("INTEGER") || dbType.toUpperCase().equals("INT")) {
            type = "Integer";
        } else if (dbType.toUpperCase().equals("TINYINT")) {
            type = "Integer";
        } else if (dbType.toUpperCase().equals("SMALLINT")) {
            type = "Integer";
        } else if (dbType.toUpperCase().equals("MEDIUMINT")) {
            type = "Integer";
        } else if (dbType.toUpperCase().equals("BIT")) {
            type = "Boolean";
        } else if (dbType.toUpperCase().equals("BIGINT")) {
            type = "Long";
        } else if (dbType.toUpperCase().equals("FLOAT")) {
            type = "Float";
        } else if (dbType.toUpperCase().equals("DOUBLE")) {
            type = "Double";
        } else if (dbType.toUpperCase().equals("DECIMAL")) {
            type = "BigDecimal";
        } else if (dbType.toUpperCase().equals("BOOLEAN")) {
            type = "Integer";
        } else if (dbType.toUpperCase().equals("DATE")) {
            type = "Date";
        } else if (dbType.toUpperCase().equals("TIME")) {
            type = "Time";
        } else if (dbType.toUpperCase().equals("DATETIME")) {
            type = "Timestamp";
        } else if (dbType.toUpperCase().equals("TIMESTAMP")) {
            type = "Timestamp";
        } else if (dbType.toUpperCase().equals("YEAR")) {
            type = "Date";
        }

        return type;

    }

}
