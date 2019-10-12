package com.cn.wavetop.dataone.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
    public static  String url = "";
    public static final String name = "com.mysql.jdbc.Driver";
    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql,String host,String user,String password,String port,String dbname) {
        try {
            Class.forName(name);//指定连接类型
            url="jdbc:mysql://"+host+"/"+dbname+"?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai";
            conn = DriverManager.getConnection(url,user,password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if(conn!=null) {
                this.conn.close();
                this.pst.close();
                System.out.println("关闭数据库连接");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
