package com.abc.test1;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * ClassName: Demo01
 * Package: com.abc.test1
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 10:28
 * @Version 1.0
 */
public class Demo01  {
    /*
     * 向Dept表增加一条数据
     *
     * */
    public static void main(String[] args) throws Exception{
        /**
         * 在查看Driver的源代码时我们发现,该类内部有一个静态代码块,
         * 在代码块中就是在实例化一个驱动并在驱动中心注册.静态代码块会在类进入内存时执行,
         * 也就是说,我们只要让该类字节码进入内存,就会自动完成注册,不需要我们手动去new
         */
        //1加载驱动 Driver
        //Driver driver = new com.mysql.cj.jdbc.Driver();
        //2注册驱动 DriverManager
        //DriverManager.registerDriver(driver);
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3获得链接 Connection
        String url="jdbc:mysql://192.168.150.88:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String user="root";
        String password="123456";

        Connection conn = DriverManager.getConnection(url,user,password);
        //4获得语句对象 Statment
        Statement statement = conn.createStatement();
        //5执行SQL语句,返回结果
        /*
         * insert delete update 操作都是调用statement.executeUpdate
         * executeUpdate返回一个int值,代表数据库多少行数据发生了变化
         * */
        String sql="insert into dept values(50,'教学部','北京');";
        int rows = statement.executeUpdate(sql);
        System.out.println("影响的行数"+rows);
        statement.close();
        conn.close();
    }

}
