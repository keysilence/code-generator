package com.silence.excel.imp.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.*;

@Controller
@RequestMapping(value = "/admin/excel/download", method = {RequestMethod.GET, RequestMethod.POST})
public class FileDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    private static Map<String, Object> map = new HashedMap();

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String index() {

        return "admin/excelDownload/list";

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

                    Iterator<Map> iterat = importExcel.iterator();
                    String data = request.getParameter("data");
                    while (iterat.hasNext()) {
                        Map<String, String> map = iterat.next();
                        String name = map.get("姓名");
                        String json = map.get(data);
                        if (!data.equals(json)) {
                            if (json.startsWith("https")) {
                                downloadFile(name + 0, json);
                            } else {
                                JSONArray jsonArray = JSON.parseArray(json);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String reportUrl = jsonObject.getString("reportUrl");
                                    downloadFile(name + i, reportUrl);
                                }
                            }
                        }
                    }

                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "admin/excelDownload/list";

    }


    /**
     * 从阿里云下载文件，准备识别
     * @param downloadUrl
     * @return
     */
    private File downloadFile(String fileName, String downloadUrl) {

        InputStream inStream = null;
        OutputStream outputStream = null;
        File targetFile = null;
        try {
            URL url = new URL(downloadUrl);
            //读到 URL连接对象
            URLConnection conn = url.openConnection();
            //读取 InputStream
            inStream = conn.getInputStream();
            targetFile = new File("/Users/Silence/Desktop/体检报告/" + fileName + downloadUrl.substring(downloadUrl.lastIndexOf(".")));
            outputStream = new FileOutputStream(targetFile);
            byte[] buf = new byte[1024];
            int n;
            while ((n = inStream.read(buf)) > 0) {
                outputStream.write(buf, 0, n);
            }
        } catch (Exception ex) {
            logger.error("Download File Error!", ex);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception ex) {
                logger.error("Stream Close Error!", ex);
            }
        }

        return targetFile;

    }

}
