package com.silence.code.generator.sm.model;

/**
 * 由于Velocity语法与替换语法相冲突，因此想到使用变量的这种方式解决问题
 * Created by Silence on 2018/3/28.
 */
public class Common {

    private String listForEach = "#foreach(${item} in $!{pageData.result})";
    private String listEnd = "#end";
    private String meta = "#parse(\"common/meta.html\")";
    private String header = "#parse(\"common/header.html\")";
    private String leftMenu = "#parse(\"common/left_menu.html\")";
    private String ifTotalCount = "#if($!pageData.totalCount > 0)";
    private String showPage = "#showPage($!{pageData},\"/admin/healthCircle/list?\")";
    private String footer = "#parse(\"common/footer.html\")";

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

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLeftMenu() {
        return leftMenu;
    }

    public void setLeftMenu(String leftMenu) {
        this.leftMenu = leftMenu;
    }

    public String getIfTotalCount() {
        return ifTotalCount;
    }

    public void setIfTotalCount(String ifTotalCount) {
        this.ifTotalCount = ifTotalCount;
    }

    public String getShowPage() {
        return showPage;
    }

    public void setShowPage(String showPage) {
        this.showPage = showPage;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}
