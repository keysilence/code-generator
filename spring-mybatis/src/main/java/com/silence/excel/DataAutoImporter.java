package com.silence.excel;

import com.sargeraswang.util.ExcelUtil.ExcelLogs;
import com.sargeraswang.util.ExcelUtil.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Silence on 2020/8/26.
 */
public class DataAutoImporter {

    private String mysqlDriver = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://10.39.32.45:31530/sydn_reform";
    private static String USER_NAME = "root";
    private static String PASSWORD ="m+1234";

    public static String DATA_TYPE = " VARCHAR ";
    public static int DATA_TYPE_SIZE = 50;

    public static void main(String[] args) throws Exception {

        File f = new File("/Users/Silence/Downloads/Test.xlsx");
        InputStream inputStream= new FileInputStream(f);

        ExcelLogs logs =new ExcelLogs();
        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd", logs , 0);

        for (Map m : importExcel) {
            System.out.println(m);
        }

        Map<String, String> titleMap = importExcel.iterator().next();
        String sqlTmp = "";

        for (Iterator<String> it = titleMap.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            sqlTmp += key + DATA_TYPE + "(" + DATA_TYPE_SIZE + "),";
        }
        String tableSql = "create table test_hh (" + sqlTmp.substring(0, sqlTmp.length() - 1) + ");";
//        String tableSql = "create table dataAutoImporter (username varchar(50) not null primary key,"
//                + "password varchar(20) not null ); ";
        DataAutoImporter dataAutoImporter = new DataAutoImporter();
        Connection conn = dataAutoImporter.getConn(URL, USER_NAME, PASSWORD);
        //创建表
        dataAutoImporter.execute(conn, tableSql);
        //存数据
        for (Map m : importExcel) {
            String sqlKeyTmp = "";
            String sqlValueTmp = "";
            for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                String value = m.get(key).toString();
                sqlKeyTmp += key + ",";
                sqlValueTmp += "'" + value + "',";
            }
            tableSql = "insert into test_hh (" + sqlKeyTmp.substring(0, sqlKeyTmp.length() - 1) + ") values ("
                + sqlValueTmp.substring(0, sqlValueTmp.length() - 1) + ");";
            dataAutoImporter.execute(conn, tableSql);
        }

    }

    /**
     * 获取链接
     * @return
     */
    public Connection getConn(String url, String userName, String password) {

        try {
            Class.forName(mysqlDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return conn;

    }

    /**
     * 执行SQL
     * @param conn
     * @param sql
     */
    public void execute(Connection conn, String sql) {

        try {
            Class.forName(mysqlDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Statement smt = conn.createStatement();
            smt.executeUpdate(sql);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

}
