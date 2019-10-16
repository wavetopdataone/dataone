package com.cn.wavetop.dataone.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.cn.wavetop.dataone.entity.SysDbinfo;
import com.cn.wavetop.dataone.entity.SysTablerule;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

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


    public static  List getConn(SysDbinfo sysDbinfo, SysTablerule sysTablerule, String sql) {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[] b= sysTablerule.getSourceTable().split(",");
        Set<String> set=new HashSet<>();
        for(int i=0;i<b.length;i++){
          set.add(b[i]);
        }
        //ArrayList<Object> data = new ArrayList<>();
        if (sysDbinfo.getType() == 2) {
            try {
                conn = DBConns.getMySQLConn(sysDbinfo);
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);
                String tableName = null;
                while (rs.next()) {
                    tableName = rs.getString(1);
                    list.add(tableName);
                    System.out.println(tableName);
                }//显示数据
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                System.out.println("連接錯誤");
                return list;

            }finally {
                DBConns.close(stmt,conn,rs);
            }
        } else if (sysDbinfo.getType() == 1) {
            String tableName = "";
            try {
                conn = DBConns.getOracleConn(sysDbinfo);
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    tableName = rs.getString(1);
                    System.out.println(tableName);
                    list.add(tableName);
                }
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                System.out.println("連接錯誤");
                return list;
            }finally {
                DBConns.close(stmt,conn,rs);
            }
        } else {
            System.out.println("類型錯誤");
            return list;
        }
        Iterator<String>  iterator=list.iterator();
        while (iterator.hasNext()) {
            String num = iterator.next();
            if (set.contains(num)) {
                iterator.remove();
            }
        }
        return list;
    }



    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        SysDbinfo mysql = SysDbinfo.builder().host("192.168.1.226").port(Long.valueOf(3306)).dbname("dataone").user("root").password("888888").build();
        Connection mySQLConn = getMySQLConn(mysql);
        System.out.println(mySQLConn);
        SysDbinfo oracle = SysDbinfo.builder().host("47.103.108.82").port(Long.valueOf(1521)).dbname("ORCL").user("zhengyong").password("zhengyong").build();
        Connection oracleConn = getOracleConn(oracle);
        System.out.println(oracleConn);
    }
}
