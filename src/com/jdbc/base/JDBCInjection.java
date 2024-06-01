package com.jdbc.base;

import java.sql.*;

public class JDBCInjection {
    public static void main(String[] args) throws Exception {
        // 1. 注册驱动（可以省略）

        // 2. 获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");

        // 3. 获取执行SQL语句的对象
        Statement statement = connection.createStatement();

        // 4. 编写SQL语句，并执行，接受返回的结果
        // String sql = "SELECT id,name,salary,age FROM user WHERE name = 'tom'";
         String injection = "abc OR '1' = '1";
        String sql = "SELECT id,name,salary,age FROM user WHERE name = '" + injection + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        // 5. 处理结果，遍历resultSet
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            int age = resultSet.getInt("age");
        }
        // 6. 释放资源(先开后关原则)
        resultSet.close();
        statement.close();
        connection.close();
    }
}
