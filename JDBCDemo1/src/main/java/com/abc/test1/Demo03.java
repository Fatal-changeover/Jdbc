package com.abc.test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName: Demo2
 * Package: com.abc.test1
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 10:55
 * @Version 1.0
 */
public class Demo03 {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.150.88:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static String user = "root";
    private static String password = "root";

    public static void main(String[] args) {

    }

    public  static void delete() {
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName(driver);
            //3获得链接 Connection
            conn = DriverManager.getConnection(url, user, password);
            //4获得语句对象 Statment
            statement = conn.createStatement();
            //5执行SQL语句,返回结果
            /*
             * insert delete update 操作都是调用statement.executeUpdate
             * executeUpdate返回一个int值,代表数据库多少行数据发生了变化
             * */
            String sql = "delete from dept where deptno =40";
            int rows = statement.executeUpdate(sql);
            System.out.println("影响的行数" + rows);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
