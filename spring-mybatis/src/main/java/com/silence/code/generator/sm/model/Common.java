package com.silence.code.generator.sm.model;

/**
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
