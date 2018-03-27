package com.silence.code.generator.sm.util;

/**
 * Created by Silence on 2018/3/26.
 */
public class FileConvert {

    public static String replaceName(String templateName, String className) {

        String name = templateName.replace("POJO", className).replace(".html", "");

        return name;

    }

    public static String replaceHtmlName(String templateName) {

        String name = templateName.substring(0, templateName.lastIndexOf("."));

        return name;

    }

}
