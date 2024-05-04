package com.abc.test4;

import java.sql.*;
import java.util.LinkedList;

public class TestTransaction2 {
    private static String driver ="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useServerPrepStmts=true&cachePrepStmts=true&&rewriteBatchedStatements=true";
    private static String user="root";
    private static String password="root";
    public static void main(String[] args) {
        testAddBatch();
    }
    // 定义一个方法,向部门表增加1000条数据
    public static void testAddBatch(){
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        LinkedList<Savepoint> savepoints =new LinkedList<Savepoint>();
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user,password);
            connection.setAutoCommit(false);
            String sql="insert into dept values (DEFAULT ,?,?)";
            preparedStatement = connection.prepareStatement(sql);//这里已经传入SQL语句
            //设置参数
            for (int i = 1; i <= 10663; i++) {
                preparedStatement.setString(1, "name");
                preparedStatement.setString(2, "loc");
                preparedStatement.addBatch();// 将修改放入一个批次中
                if(i%1000==0){
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();// 清除批处理中的数据
                    // 设置回滚点
                    Savepoint savepoint = connection.setSavepoint();
                    savepoints.addLast(savepoint);
                }
                // 数据在 100001条插入的时候出现异常
                if(i ==10001){
                    int x =1/0;
                }
            }
            /*
            * 整数数组中的元素代表执行的结果代号
            * SUCCESS_NO_INFO -2
            * EXECUTE_FAILED  -3
            * */
            /*int[] ints = */
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }catch (Exception e){
            if(null != connection){
                try {
                    //Savepoint sp = savepoints.getLast();
                    Savepoint sp = savepoints.get(4);
                    if(null != sp){
                        // 选择回滚点
                        connection.rollback(sp);// 回滚
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if(null != connection){
                try {
                    connection.commit();// 提交
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