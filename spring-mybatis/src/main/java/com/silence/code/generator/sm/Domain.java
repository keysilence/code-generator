package com.silence.code.generator.sm;

import com.silence.code.generator.sm.model.Column;

import java.util.List;

/**
 * Created by Silence on 2018/3/26.
 */
public class Domain {

    private String packageName;
    private List<Column> columns;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
