package com.abc.test1;

import com.abc.pojo.Emp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Demo2
 * Package: com.abc.test1
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 10:55
 * @Version 1.0
 */
public class Demo05 {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.150.88:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static String user = "root";
    private static String password = "123456";

    public static void main(String[] args) {
        List<Emp> emps = testQuery();
        // 遍历集合
        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

    public  static List<Emp> testQuery() {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Emp> list = null;
        try {
            Class.forName(driver);
            //3获得链接 Connection
            conn = DriverManager.getConnection(url, user, password);
            //4获得语句对象 Statment
            statement = conn.createStatement();
            String sql="select * from emp";
            resultSet = statement.executeQuery(sql);
            list=new ArrayList<>();
            while(resultSet.next()){
                int empno = resultSet.getInt("empno");
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int mgr = resultSet.getInt("mgr");
                Date hiredate = resultSet.getDate("hiredate");
                double sal= resultSet.getDouble("sal");
                double comm= resultSet.getDouble("comm");
                int deptno= resultSet.getInt("deptno");
                Emp emp = new Emp(empno, ename, job, mgr, hiredate, sal, comm, deptno);
                list.add(emp);
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
        return list;
    }
}
