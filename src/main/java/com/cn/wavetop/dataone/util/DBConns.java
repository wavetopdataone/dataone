package com.cn.wavetop.dataone.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.cn.wavetop.dataone.entity.SysDbinfo;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBConns {

    /**
     * 获取Mysql对象
     * @return
     */
    public static Connection getMySQLConn(SysDbinfo sysDbinfo) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String url="jdbc:mysql://"+sysDbinfo.getHost()+":"+sysDbinfo.getPort()+"/"+sysDbinfo.getDbname()+"?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai";
        Class.forName("com.mysql.jdbc.Driver");
        return  DriverManager.getConnection(url, sysDbinfo.getUser(), sysDbinfo.getPassword());
    }
    /**
     * 获取Oracle对象
     * @return
     */
    public static Connection getOracleConn(SysDbinfo sysDbinfo) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String url = "jdbc:oracle:thin:@"+sysDbinfo.getHost()+":"+sysDbinfo.getPort()+":"+sysDbinfo.getDbname();
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return  DriverManager.getConnection(url, sysDbinfo.getUser(), sysDbinfo.getPassword());

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
    public static void close( Connection connection) throws SQLException {
        close(null, connection, null);
    }



    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        SysDbinfo mysql = SysDbinfo.builder().host("192.168.1.226").port(Long.valueOf(3306)).dbname("dataone").user("root").password("888888").build();
        Connection mySQLConn = getMySQLConn(mysql);

        String sql = "";
        ArrayList<Object> data = new ArrayList<>();
        ArrayList<Object> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        sql = "select column_name,data_type,CHARACTER_MAXIMUM_LENGTH from information_schema.columns where 1=0 ";

            conn = DBConns.getMySQLConn(mysql);
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("1");
                list.clear();
                list.add(rs.getString("column_name"));
                list.add(rs.getString("data_type"));
                list.add(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
                data.add(list);
            }
        System.out.println(mySQLConn);
        SysDbinfo oracle = SysDbinfo.builder().host("47.103.108.82").port(Long.valueOf(1521)).dbname("ORCL").user("zhengyong").password("zhengyong").build();
        Connection oracleConn = getOracleConn(oracle);
        System.out.println(oracleConn);
    }
}
