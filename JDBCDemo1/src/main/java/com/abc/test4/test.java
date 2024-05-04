package com.abc.test4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ClassName: test
 * Package: com.abc.test4
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 13:14
 * @Version 1.0
 */
public class test {
    public class TestTransaction {
        private static String driver ="com.mysql.cj.jdbc.Driver";
        private static String url="jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useServerPrepStmts=true&cachePrepStmts=true&&rewriteBatchedStatements=true";
        private static String user="root";
        private static String password="root";
        public static void main(String[] args) {
            testTransaction();
        }
        // 定义一个方法,向部门表增加1000条数据
        public static void testTransaction(){
            Connection connection = null;
            PreparedStatement preparedStatement=null;
            /*
             * JDBC 默认是自动提交事务
             * 每条DML都是默认提交事务的,多个preparedStatement.executeUpdate();都会提交一次事务
             * 如果想手动控制事务,那么就不能让事务自动提交
             * 通过Connection对象控制connection.setAutoCommit(false);
             * 如果不设置 默认值为true,自动提交,设置为false之后就是手动提交了
             * 无论是否发生回滚,事务最终会一定要提交的 提交我们建议放在finally之中进行提交
             * 如果是转账的过程中出现异常了,那么我们就要执行回滚,回滚操作应该方法catch语句块中
             *
             * */
            try{
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user,password);
                // 设置事务手动提交
                connection.setAutoCommit(false);
                String sql="update account set money =money- ? where aid = ?";
                preparedStatement = connection.prepareStatement(sql);//这里已经传入SQL语句
                // 转出
                preparedStatement.setDouble(1, 100);
                preparedStatement.setInt(2, 1);
                preparedStatement.executeUpdate();
                // 产生异常
                //int i =1/0;
                // 转入
                preparedStatement.setDouble(1, -100);
                preparedStatement.setInt(2, 2);
                preparedStatement.executeUpdate();
            }catch (Exception e){
                if(null != connection){
                    try {
                        connection.rollback();// 回滚事务
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                e.printStackTrace();
            }finally {
                // 提交事务
                if(null != connection){
                    try {
                        connection.commit();
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
        }
    }

}
