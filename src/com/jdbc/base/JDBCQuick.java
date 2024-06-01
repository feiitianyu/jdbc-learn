package com.jdbc.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception {
        // 1. 注册驱动(将数据库厂商提供的驱动类，通过类加载的方式加载到程序当中)
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获取连接对象
        String url = "jdbc:mysql://localhost:3306/learn-jdbc";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);
        // 3. 获取执行SQL语句的对象
        Statement statement = connection.createStatement();
        // 4. 编写SQL语句，并执行，接受返回的结果集
        String sql = "SELECT id,name,salary,age FROM user";
        ResultSet resultSet = statement.executeQuery(sql);
        // 5. 处理结果，遍历resultSet结果集
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
