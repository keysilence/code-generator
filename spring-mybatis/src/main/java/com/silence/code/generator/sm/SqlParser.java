package com.silence.code.generator.sm;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;
import com.silence.code.generator.sm.model.Class;
import com.silence.code.generator.sm.model.Column;
import com.silence.code.generator.sm.model.Common;
import com.silence.code.generator.sm.model.Table;
import com.silence.code.generator.sm.util.ClassConvert;
import com.silence.code.generator.sm.util.ColumnConvert;
import com.silence.code.generator.sm.util.FileConvert;
import com.silence.code.generator.sm.util.TypeConvert;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Silence on 2018/3/24.
 */
@Repository("sqlParser")
public class SqlParser {

    public Table table = null;

    public Class classInfo = null;

    public Common common = null;

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

    public void parseCreate(String sql) {

//        // 新建 MySQL Parser
//        SQLStatementParser parser = new MySqlStatementParser(sql);
//
//        // 使用Parser解析生成AST，这里SQLStatement就是AST
//        SQLStatement statement = parser.parseStatement();
//
//        // 使用visitor来访问AST
//        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//        statement.accept(visitor);

        List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);

        classInfo = new Class();

        MySqlCreateTableStatement mySqlCreateTableStatement = (MySqlCreateTableStatement) sqlStatementList.get(0);

        List<SQLObject> sqlObjects = mySqlCreateTableStatement.getChildren();

        table = new Table();
        List<Column> columns = new ArrayList<>();

        int size = sqlObjects.size();
        int index = 0;
        for (SQLObject sqlObject : sqlObjects) {
            String obj = sqlObject.toString();
            String[] split = obj.split(" ");
            if (split.length > 1) {
                Column column = new Column();
                String dbName = split[0].replaceAll("`", "");
                column.setDbName(dbName);
                column.setName(ColumnConvert.getName(dbName));
                column.setType(TypeConvert.getType(split[1].split("\\(")[0]));
                if (index == size - 1) {
                    column.setLast(true);
                } else {
                    column.setLast(false);
                }
                columns.add(column);
            }
            index++;
        }
        table.setColumns(columns);
        String tableName = mySqlCreateTableStatement.getName().getSimpleName().replaceAll("`", "");
        table.setName(tableName);

        //获取表名
        classInfo.setClassName(ClassConvert.getName(tableName));

    }

    public void velocity() throws Exception {

        File directory = new File("");
        String path = directory.getAbsolutePath() + File.separator + "spring-mybatis" + File.separator + "output";
        System.out.println(path);
        File file = new File(path + File.separator + "aaa.java");

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template t = ve.getTemplate("templates/POJO.java.html");
        System.out.println(t.getName());
        VelocityContext ctx = new VelocityContext();

//        com.silence.code.generator.sm.model.Class aClass = new com.silence.code.generator.sm.model.Class();
//        aClass.setPackageName("com.silence");
//        aClass.setClassName("Test");

//        Table table = new Table();
//        List<Column> columns = new ArrayList<>();
//        Column column = new Column();
//        column.setName("Hello");
//        column.setType("String");
//        column.setAnnotation("测试");
//        columns.add(column);
//        table.setName("test");
//        table.setColumns(columns);

        ctx.put("class", classInfo);
        ctx.put("table", table);

//        StringWriter sw = new StringWriter();
        FileWriter fw = new FileWriter(file);

        t.merge(ctx, fw);

        fw.flush();
        fw.close();

//        System.out.println(sw.toString());

    }

    public void velocityPOJO(String path) throws Exception {

        String templateName = "POJO.java.html";

        handle(path, templateName);

    }

    public void velocityPOJOController(String path) throws Exception {

        String templateName = "POJOController.java.html";

        handle(path, templateName);

    }

    public void velocityPOJOMapper(String path) throws Exception {

        String templateName = "POJOMapper.java.html";

        handle(path, templateName);

    }

    public void velocityPOJOMapperXML(String path) throws Exception {

        String templateName = "POJOMapper.xml.html";

        handle(path, templateName);

    }

    public void velocityPOJOService(String path) throws Exception {

        String templateName = "POJOService.java.html";

        handle(path, templateName);

    }

    public void velocityPOJOServiceImpl(String path) throws Exception {

        String templateName = "POJOServiceImpl.java.html";

        handle(path, templateName);

    }

    public void velocityPOJORestController(String path) throws Exception {

        String templateName = "POJORestController.java.html";

        handle(path, templateName);

    }

    public void velocityListHtml(String path) throws Exception {

        String templateName = "list.html.html";

        handle(path, templateName);

    }

    public void velocityEditHtml(String path) throws Exception {

        String templateName = "edit.html.html";

        handle(path, templateName);

    }

    private void handle(String path, String templateName) throws Exception {

        Template t = ve.getTemplate("templates" + File.separator + templateName);

        VelocityContext ctx = new VelocityContext();

        ctx.put("class", classInfo);
        ctx.put("table", table);
        ctx.put("common", new Common());

        File file = new File(path + File.separator + FileConvert.replaceHtmlName(templateName));

        FileWriter fw = new FileWriter(file);

        t.merge(ctx, fw);

        fw.flush();
        fw.close();

    }

    public void velocityAll(String path) throws Exception {

        velocityPOJO(path);

        velocityPOJOController(path);

        velocityPOJOMapper(path);

        velocityPOJOMapperXML(path);

        velocityPOJOService(path);

        velocityPOJOServiceImpl(path);

        velocityPOJORestController(path);

        velocityListHtml(path);

        velocityEditHtml(path);

    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Class getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(Class classInfo) {
        this.classInfo = classInfo;
    }

    public static void main(String[] args) throws Exception {

        String sql = "CREATE TABLE `comment_praise` (\n" +
                "    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                "    `comment_id` bigint(20) NOT NULL,\n" +
                "    `type` tinyint(4) NOT NULL,\n" +
                "    `uid` bigint(20) NOT NULL,\n" +
                "    `create_time` bigint(20) NOT NULL,\n" +
                "    `update_time` bigint(20) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        SqlParser sqlParser = new SqlParser();
        sqlParser.parseCreate(sql);

        File directory = new File("");
        String path = directory.getAbsolutePath() + File.separator + "spring-mybatis" + File.separator + "output";

//        velocityPOJO(path);
//
//        velocityPOJOController(path);
//
//        velocityPOJOMapper(path);
//
//        velocityPOJOMapperXML(path);
//
//        velocityPOJOService(path);
//
//        velocityPOJOServiceImpl(path);
//
//        velocityPOJORestController(path);

        sqlParser.velocityListHtml(path);

//        velocityEditHtml(path);

    }

}
