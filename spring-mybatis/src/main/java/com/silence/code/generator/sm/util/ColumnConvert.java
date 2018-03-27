package com.silence.code.generator.sm.util;

/**
 * Created by Silence on 2018/3/26.
 */
public class ColumnConvert {

    public static String getName(String dbName) {

        if (!dbName.contains("_")) {
            return dbName;
        }

        String[] split = dbName.split("_");
        StringBuffer sb = new StringBuffer(split[0]);
        for (int i = 1; i < split.length; i++) {
            String sp = split[i];
            sb.append(sp.substring(0, 1).toUpperCase());
            sb.append(sp.substring(1));
        }

        return sb.toString();

    }

}
