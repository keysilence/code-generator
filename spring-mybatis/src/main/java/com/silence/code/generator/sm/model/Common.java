package com.silence.code.generator.sm.model;

/**
 * 由于Velocity语法与替换语法相冲突，因此想到使用变量的这种方式解决问题
 * Created by Silence on 2018/3/28.
 */
public class Common {

    private String listForEach = "#foreach(${item} in $!{pageData.result})";
    private String listEnd = "#end";

    public String getListForEach() {
        return listForEach;
    }

    public void setListForEach(String listForEach) {
        this.listForEach = listForEach;
    }

    public String getListEnd() {
        return listEnd;
    }

    public void setListEnd(String listEnd) {
        this.listEnd = listEnd;
    }
}
