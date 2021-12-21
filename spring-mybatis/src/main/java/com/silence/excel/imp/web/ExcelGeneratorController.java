package com.silence.excel.imp.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sargeraswang.util.ExcelUtil.ExcelLogs;
import com.sargeraswang.util.ExcelUtil.ExcelUtil;
import com.silence.code.generator.sm.model.Common;
import com.silence.code.generator.sm.util.FileConvert;
import org.apache.commons.collections.map.HashedMap;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Controller
@RequestMapping(value = "/admin/excel/generator", method = {RequestMethod.GET, RequestMethod.POST})
public class ExcelGeneratorController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelGeneratorController.class);

    private static Map<String, Object> map = new HashedMap();

    public static VelocityEngine ve = null;

    static {

        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        //解决乱码问题
        ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        ve.init();

    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String index() {

        return "admin/excelGenerator/list";

    }

    @RequestMapping(value = "/do", method = {RequestMethod.GET, RequestMethod.POST})
    public String upload(HttpServletRequest request,
                         Model view) throws IllegalStateException, IOException {

        try {
            String uuid = UUID.randomUUID().toString();
            view.addAttribute("uuid", uuid);
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());

            //检查form中是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)) {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iterator = multiRequest.getFileNames();

                while(iterator.hasNext()) {

                    //一次遍历所有文件
                    MultipartFile file = multiRequest.getFile(iterator.next().toString());
                    File importFile = null;
                    String path = null;
                    if (file != null && file.getSize() > 0 && (file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"))) {
                        path = request.getRealPath("") + File.separator + "output" + File.separator + uuid;
                        File filePath = new File(path);
                        if (!filePath.exists()) {
                            filePath.mkdir();
                        }
                        importFile = new File(path + File.separator + file.getOriginalFilename());
                        //上传
                        file.transferTo(importFile);
                        map.put(uuid, importFile);
                    } else {
                        continue;
                    }

                    InputStream inputStream= new FileInputStream(importFile);

                    ExcelLogs logs = new ExcelLogs();
                    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd", logs , 0);

                    Iterator<Map> iterat = importExcel.iterator();
                    this.velocityTmpAdd(path, iterat);
//                    this.velocityTmpReplace(path, iterat);

                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "admin/excelGenerator/list";

    }


    /**
     * 从阿里云下载文件，准备识别
     * @return
     */
    private void velocityTmpAdd(String path, Iterator<Map> mapIterator) {

        try {
            String templateName = "Tmp.java.html";

            handleAdd(path, templateName, mapIterator);
        } catch (Exception ex) {

        }

    }

    private void handleAdd(String path, String templateName, Iterator<Map> mapIterator) throws Exception {

        Template t = ve.getTemplate("templates" + File.separator + templateName);
        Table table = new Table();
        List<Column> columns = new ArrayList<>();
        table.setColumns(columns);
        List<Field> fields = new ArrayList<>();
        Column column = new Column();
        String tmpName = "";
        Map<String, String> tmpMap = new HashMap<>();
        while (mapIterator.hasNext()) {
            Map<String, String> map = mapIterator.next();
            String name = map.get("姓名");
            if ("姓名".equals(name)) {
                continue;
            }
            String mobile = map.get("电话号码");
            String sex = map.get("性别");
            String code = map.get("症状code");
            if (!tmpName.equals(name)) {
                column = new Column();
                column.setName(name);
                column.setMobile(mobile);
                column.setSex("男".equals(sex) ? "1" : "2");
                columns.add(column);
                fields = new ArrayList<>();
                column.setFields(fields);
            }
            Field field = new Field();
            field.setCode(code);
            fields.add(field);
            tmpName = name;
            if (tmpMap.containsKey(name)) {
                String value = tmpMap.get(name);
                if (!value.equals(mobile)) {
                    System.out.println("姓名：" + name);
                }
            }
            tmpMap.put(name, mobile);
        }
        VelocityContext ctx = new VelocityContext();

        ctx.put("table", table);
        File file = new File(path + File.separator + FileConvert.replaceName(templateName, "TmpAdd.java"));

        FileWriter fw = new FileWriter(file);

        t.merge(ctx, fw);

        fw.flush();
        fw.close();

    }

    /**
     * 从阿里云下载文件，准备识别
     * @return
     */
    private void velocityTmpReplace(String path, Iterator<Map> mapIterator) {

        try {
            String templateName = "TmpReplace.java.html";

            handleReplace(path, templateName, mapIterator);
        } catch (Exception ex) {

        }

    }

    private void handleReplace(String path, String templateName, Iterator<Map> mapIterator) throws Exception {

        Template t = ve.getTemplate("templates" + File.separator + templateName);
        Table table = new Table();
        List<Column> columns = new ArrayList<>();
        table.setColumns(columns);
        List<Field> fields = new ArrayList<>();
        Column column = new Column();
        String tmpName = "";
        while (mapIterator.hasNext()) {
            Map<String, String> map = mapIterator.next();
            String name = map.get("姓名");
            if ("姓名".equals(name)) {
                continue;
            }
            String mobile = map.get("电话号码");
            String sex = map.get("性别");
            String code = map.get("症状code");
            String diseaseName = map.get("被替换症状");
            if (!tmpName.equals(name)) {
                column = new Column();
                column.setName(name);
                column.setMobile(mobile);
                column.setSex("男".equals(sex) ? "1" : "2");
                columns.add(column);
                fields = new ArrayList<>();
                column.setFields(fields);
            }
            Field field = new Field();
            field.setCode(code);
            field.setName(diseaseName);
            fields.add(field);
            tmpName = name;
        }
        VelocityContext ctx = new VelocityContext();

        ctx.put("table", table);
        File file = new File(path + File.separator + FileConvert.replaceName(templateName, "TmpReplace.java"));

        FileWriter fw = new FileWriter(file);

        t.merge(ctx, fw);

        fw.flush();
        fw.close();

    }

    public class Table {

        private List<Column> columns;

        public List<Column> getColumns() {
            return columns;
        }

        public void setColumns(List<Column> columns) {
            this.columns = columns;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Table{");
            sb.append("columns=").append(columns);
            sb.append('}');
            return sb.toString();
        }
    }

    public class Column {

        private String name;
        private String mobile;
        private String sex;
        private List<Field> fields;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public List<Field> getFields() {
            return fields;
        }

        public void setFields(List<Field> fields) {
            this.fields = fields;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Column{");
            sb.append("name='").append(name).append('\'');
            sb.append(", mobile='").append(mobile).append('\'');
            sb.append(", sex='").append(sex).append('\'');
            sb.append(", fields=").append(fields);
            sb.append('}');
            return sb.toString();
        }
    }

    public class Field {

        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Field{");
            sb.append("code='").append(code).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}
