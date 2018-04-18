package com.silence.code.generator.sm.model;

/**
 * Created by Silence on 2018/3/26.
 */
public class Class {

    private String packageName = "com.silence";
    private String className;
    private String classNameLower;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameLower() {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    public void setClassNameLower(String classNameLower) {
        this.classNameLower = classNameLower;
    }

    @Override
    public String toString() {
        return "Class{" +
                "packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", classNameLower='" + classNameLower + '\'' +
                '}';
    }

}
