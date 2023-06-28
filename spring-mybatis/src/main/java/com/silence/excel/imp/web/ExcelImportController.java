package com.silence.excel.imp.web;

import com.sargeraswang.util.ExcelUtil.ExcelLogs;
import com.sargeraswang.util.ExcelUtil.ExcelUtil;
import com.silence.excel.DataAutoImporter;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.*;

@Controller
@RequestMapping(value = "/admin/excel/import", method = {RequestMethod.GET, RequestMethod.POST})
public class ExcelImportController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImportController.class);

    private static Map<String, Object> map = new HashedMap();

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String index() {

        return "admin/excelImport/list";

    }

    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String edit(String uuid, String tableName, String dataSource, String userName, String password,
                       String[] columnName, String[] columnType, String[] annotation, HttpServletRequest request) {


        Map<String, String> aName = new HashedMap();
        Map<String, String> aType = new HashedMap();
        for (int i = 0; i < annotation.length; i++) {
            String tmp = annotation[i];
            aName.put(tmp, columnName[i]);
            aType.put(tmp, columnType[i]);
        }
        File importFile = (File) map.get(uuid);
        try {
            InputStream inputStream= new FileInputStream(importFile);

            ExcelLogs logs = new ExcelLogs();
            Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd", logs , 0);

            for (Map m : importExcel) {
                System.out.println(m);
            }

            //Map<String, String> titleMap = importExcel.iterator().next();
            //String sqlTmp = "";
            //
            //for (Iterator<String> it = titleMap.keySet().iterator(); it.hasNext();) {
            //    String key = it.next();
            //    sqlTmp += "".endsWith(aName.get(key)) ? key : aName.get(key) + " " + aType.get(key) + ",";
            //}
            //String tableSql = "create table " + tableName + " (" + sqlTmp.substring(0, sqlTmp.length() - 1) + ");";
//        String tableSql = "create table dataAutoImporter (username varchar(50) not null primary key,"
//                + "password varchar(20) not null ); ";
            DataAutoImporter dataAutoImporter = new DataAutoImporter();
            Connection conn = dataAutoImporter.getConn(dataSource, userName, password);
            ////创建表
            //dataAutoImporter.execute(conn, tableSql);
            //存数据
            for (Map m : importExcel) {
                String sqlKeyTmp = "";
                String sqlValueTmp = "";
                for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
                    String key = it.next();
                    String value = m.get(key).toString();
                    sqlKeyTmp += "".endsWith(aName.get(key)) ? key : aName.get(key) + ",";
                    //sqlValueTmp += "'" + value.trim() + "',";
                    sqlValueTmp += value.trim();
                }
                //tableSql = "insert into " + tableName + " (" + sqlKeyTmp.substring(0, sqlKeyTmp.length() - 1) + ") values ("
                //        + sqlValueTmp.substring(0, sqlValueTmp.length() - 1) + ");";
                String tableSql = "select ID from " + tableName + " WHERE NAME = '" + sqlValueTmp + "'";
                //dataAutoImporter.execute(conn, tableSql);
                dataAutoImporter.executeQuery(conn, tableSql, sqlValueTmp);
            }
            importFile.delete();
            String path = request.getRealPath("") + File.separator + "output" + File.separator + uuid;
            File filePath = new File(path);
            if (filePath.exists()) {
                filePath.delete();
            }
            map.remove(uuid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "success";

    }

    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
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
                    if (file != null && file.getSize() > 0 && (file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"))) {
                        String path = request.getRealPath("") + File.separator + "output" + File.separator + uuid;
                        File filePath = new File(path);
                        if (!filePath.exists()) {
                            filePath.mkdir();
                        }
                        importFile = new File(path + File.separator + file.getOriginalFilename());
                        //上传
                        file.transferTo(importFile);
                        map.put(uuid, importFile);
                    }

                    InputStream inputStream= new FileInputStream(importFile);

                    ExcelLogs logs = new ExcelLogs();
                    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd", logs , 0);

                    Map<String, String> titleMap = importExcel.iterator().next();

                    List<String> columns = new ArrayList<>();

                    for (Iterator<String> it = titleMap.keySet().iterator(); it.hasNext();) {
                        String key = it.next();
                        columns.add(key);
                    }

                    view.addAttribute("columns", columns);

                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "admin/excelImport/edit";

    }

}
