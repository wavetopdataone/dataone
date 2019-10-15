package com.cn.wavetop.dataone.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.cn.wavetop.dataone.entity.SysDbinfo;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConns {

    private static DataSource ds;
    /**
     * 获取连接对象
     * @return
     */
    public static Connection getMySQLConn(SysDbinfo sysDbinfo) throws SQLException {
        // 加载配置文件
        Properties pro = new Properties();
        String url = url="jdbc:mysql://"+sysDbinfo.getHost()+":"+sysDbinfo.getPort()+"/"+sysDbinfo.getDbname()+"?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai";
        pro.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        pro.setProperty("url",url);
        pro.setProperty("username", sysDbinfo.getUser());
        pro.setProperty("password", sysDbinfo.getPassword());
        try {
            // 获取连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds.getConnection();
    }

    /**
     * 获取连接对象
     * @return
     */
    public static Connection getOracleConn(SysDbinfo sysDbinfo) throws SQLException {
        // 加载配置文件
        Properties pro = new Properties();
        String url = "jdbc:oracle:thin:@"+sysDbinfo.getHost()+":"+sysDbinfo.getPort()+":"+sysDbinfo.getDbname();

        pro.setProperty("driverClassName", "oracle.jdbc.driver.OracleDriver");
        pro.setProperty("url",url);
        pro.setProperty("user", sysDbinfo.getUser());
        pro.setProperty("password", sysDbinfo.getPassword());
        try {
            // 获取连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds.getConnection();
    }


    public static void main(String[] args) throws SQLException {
        SysDbinfo mysql = SysDbinfo.builder().host("192.168.1.226").port(Long.valueOf(3306)).dbname("dataone").user("root").password("888888").build();
        Connection mySQLConn = getMySQLConn(mysql);
        SysDbinfo oracle = SysDbinfo.builder().host("47.103.108.82").port(Long.valueOf(1521)).dbname("ORCL").user("zhengyong").password("zhengyong").build();
        Connection oracleConn = getMySQLConn(oracle);
    }
    /**
     * 释放资源
     *
     */
    public static void close(Statement stmt, Connection connection, ResultSet rs) {
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Statement stmt, Connection connection) throws SQLException {
        close(stmt, connection, null);
    }

    /**
     * 获取连接池的方法
     *
     * @return
     */
    public  static DataSource getDataSources(){
        return ds;
    }
}
