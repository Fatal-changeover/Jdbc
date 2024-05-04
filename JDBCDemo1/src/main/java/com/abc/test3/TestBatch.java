package com.abc.test3;

/**
 * ClassName: TestBatch
 * Package: com.abc.test3
 * Description:
 *
 * @Author R
 * @Create 2024/5/4 12:33
 * @Version 1.0
 */

import java.nio.channels.NonWritableChannelException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 批处理
 */
public class TestBatch {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useServerPrepStmts=true&cachePrepStmts=true&&rewriteBatchedStatements=true\";\n" +
            "    private static String user=\"root\";";
    private static String user = "root";
    private static String password = "123456";

    public static void main(String[] args) {
        testAddBatch();
    }
    // 定义一个方法,向部门表增加1000条数据
    public static void testAddBatch(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            String sql="insert into dept values (DEFAULT ,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 10663; i++) {
                preparedStatement.setString(1, "name");
                preparedStatement.setString(2, "loc");
                preparedStatement.addBatch();// 将修改放入一个批次中
                if(i%1000==0){
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();// 清除批处理中的数据
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();

        } catch (Exception e) {
            throw new RuntimeException(e);
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
}
