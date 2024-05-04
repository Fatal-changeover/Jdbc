package com.abc.test3;

import com.abc.pojo.Emp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: TestPreparedStatement
 * Package: com.abc.test3
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 12:18
 * @Version 1.0
 */
public class TestPreparedStatement {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.150.88:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static String user = "root";
    private static String password = "123456";

    public static void main(String[] args) {
            testQuery();
    }

    // 向 Emp表中增加一条数据
    public static void testAdd() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            String sql = "insert into emp values(DEFAULT ,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Mark");
            preparedStatement.setString(2, "MANAGER");
            preparedStatement.setInt(3, 7839);
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
            preparedStatement.setDouble(5, 3000.12);
            preparedStatement.setDouble(6, 0.0);
            preparedStatement.setDouble(7, 30);
            int rows = preparedStatement.executeUpdate();
            System.out.println(rows);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void testDelete() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            String sql = "update emp set ename =? ,job=? where empno =?";
            preparedStatement = connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setString(1, "Jhon");
            preparedStatement.setString(2, "ANALYST");
            preparedStatement.setInt(3, 7935);
            int rows = preparedStatement.executeUpdate();
            System.out.println(rows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void testUpdate() {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user,password);
            String sql="delete from emp where empno =?";
            preparedStatement = connection.prepareStatement(sql);//这里已经传入SQL语句
            //设置参数
            preparedStatement.setInt(1,7935);
            //执行CURD
            int rows =preparedStatement.executeUpdate();// 这里不需要再传入SQL语句
            System.out.println(rows);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void testQuery() {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Emp> list =null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            String sql="select * from emp where ename like ? ";
             preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%A%");
             resultSet = preparedStatement.executeQuery();
            list= new ArrayList<>();
            while(resultSet.next()){
                int empno = resultSet.getInt("empno");
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int mgr = resultSet.getInt("mgr");
                Date hiredate = resultSet.getDate("hiredate");
                double sal= resultSet.getDouble("sal");
                double comm= resultSet.getDouble("comm");
                int deptno= resultSet.getInt("deptno");
                Emp emp =new Emp(empno, ename, job, mgr, hiredate, sal, comm, deptno);
                list.add(emp);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(null != resultSet){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        // 遍历集合
        for (Emp emp : list) {
            System.out.println(emp);
        }
    }
}
