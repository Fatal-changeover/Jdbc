package com.abc.test1;

import com.abc.pojo.Account;
import com.abc.pojo.Emp;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

/**
 * ClassName: Demo2
 * Package: com.abc.test1
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 10:55
 * @Version 1.0
 */
public class Demo06 {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.150.88:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static String user = "root";
    private static String password = "123456";

    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("请输入用户名");
        String username=sc.next();
        System.out.println("请输入密码");
        String pwd =sc.next();
        Account account = getAccount(username, pwd);
        System.out.println(null!= account?"登录成功":"登录失败");
        sc.close();
    }

    public  static Account getAccount(String username,String pwd) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Account account =null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user,password);
            /**
             * 1使用PreparedStatement语句对象防止注入攻击
             * 2PreparedStatement 可以使用 ? 作为参数的占位符
             * 3使用?作为占位符,即使是字符串和日期类型,也不使用单独再添加 ''
             * 4connection.createStatement();获得的是普通语句对象 Statement
             * 5connection.prepareStatement(sql);可以获得一个预编译语句对象PreparedStatement
             * 6如果SQL语句中有?作为参数占位符号,那么要在执行CURD之前先设置参数
             * 7通过set***(问号的编号,数据) 方法设置参数
             */
            statement = conn.createStatement();
            String sql="select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,pwd);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()) {
                int aid = resultSet.getInt("aid");
                String usernamea = resultSet.getString("username");
                String pwda = resultSet.getString("password");
                double money = resultSet.getDouble("money");
                account=new Account(aid,usernamea,pwda,money);
                System.out.println(account);
            }


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
        return account;
    }
}
