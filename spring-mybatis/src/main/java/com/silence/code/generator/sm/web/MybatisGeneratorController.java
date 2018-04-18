package com.silence.code.generator.sm.web;

import com.silence.code.generator.sm.SqlParser;
import com.silence.code.generator.sm.model.Class;
import com.silence.code.generator.sm.model.Column;
import com.silence.code.generator.sm.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/mybatisGenerator", method = {RequestMethod.GET, RequestMethod.POST})
public class MybatisGeneratorController {

    private static final Logger logger = LoggerFactory.getLogger(MybatisGeneratorController.class);

    @Autowired
    private SqlParser sqlParser;

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public String edit(@RequestParam(value = "sql") String sql,
                       Model view) {
        try {
            sqlParser.parseCreate(sql);
            view.addAttribute("table", sqlParser.getTable());
            view.addAttribute("class", sqlParser.getClassInfo());
            view.addAttribute("classInfo", sqlParser.getClassInfo());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "admin/mybatisGenerator/edit";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public String save(@RequestParam(value = "className") String className,
                       @RequestParam(value = "packageName") String packageName,
                       @RequestParam(value = "annotation") String[] annotation,
                       HttpServletRequest request,
                       Model view) {

        try {
            Class classInfo = sqlParser.getClassInfo();
            classInfo.setClassName(className);
            classInfo.setPackageName(packageName);
            Table table = sqlParser.getTable();
            List<Column> columns = table.getColumns();
            for (int i = 0; i < columns.size(); i++ ) {
                Column column = columns.get(i);
                column.setAnnotation(annotation[i]);
            }
            String path = request.getRealPath("") + File.separator + "output";
            File directory = new File(path);
            File[] files = directory.listFiles();
            for (File file: files) {
                file.delete();
            }
            sqlParser.velocityAll(path);
            String[] fileArray = directory.list();
            view.addAttribute("files", fileArray);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "admin/mybatisGenerator/download";

    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "id", required = false) Long id,
                       Model view) {

        return "admin/mybatisGenerator/list";

    }

    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String download(@RequestParam(value = "fileName") String fileName,
                           HttpServletRequest request,
                           HttpServletResponse response, Model view) {

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            String path = request.getRealPath("") + File.separator + "output";
            //打开本地文件流
            InputStream inputStream = new FileInputStream(path + File.separator + fileName);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "";

    }

}
