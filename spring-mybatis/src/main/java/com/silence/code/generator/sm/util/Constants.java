package com.silence.code.generator.sm.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by yhy on 9/4/15.
 */
public class Constants {

    //1:新闻;2:服务;3:活动;4:发现;5:大讲堂
    public static Integer TYPE_NEWS = 1;
    public static Integer TYPE_SERVICES = 2;
    public static Integer TYPE_ACTIVITY = 3;
    public static Integer TYPE_CIRCLE = 4;
    public static Integer TYPE_COURSE = 5;


    //iframe,必须最后关闭窗口，否则执行不了刷新动作。
    public static final String WEB_IFRAME_SCRIPT = "<script type='text/javascript'>" +
            "parent.layer.msg('%s', {icon: 1,time: 1000}, function(){" +
            "   parent.location.reload();" +
            //"   parent.layer.close(parent.layer.getFrameIndex(window.name));" +
            "});" +
            "</script>";

    public static final String WEB_IFRAME_SCRIPT2 = "<script type='text/javascript'>" +
            "parent.layer.alert('%s', {title : false ,closeBtn: 0}, function(){" +
            "   parent.location.reload();" +
            //"   parent.layer.close(parent.layer.getFrameIndex(window.name));" +
            "});" +
            "</script>";

    public static final String WEB_IFRAME_ERROR_SCRIPT = "<script type='text/javascript'>" +
            "parent.layer.msg('%s', {icon: 1,time: 1000}, function(){" +
//            "   parent.location.reload();" +
            //"   parent.layer.close(parent.layer.getFrameIndex(window.name));" +
            "});" +
            "</script>";

    public static String formatJsMessage(String msgStr) {
        String msg = StringEscapeUtils.escapeJavaScript(msgStr);
        return String.format(Constants.WEB_IFRAME_SCRIPT, msg);
    }

    //设置成功代码200
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;
    //成功，失败json。
    //public static ResultJson SUCCESS = new ResultJson(SUCCESS_CODE, "success");
    //public static ResultJson ERROR = new ResultJson(ERROR_CODE, "error");

}
