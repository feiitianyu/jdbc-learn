package com.jdbc.base;

import org.junit.Test;

import java.sql.*;

public class JDBCOperation {
    @Test
    public void testQuerySingleRowAndCol() throws Exception {
        // 1. 注册驱动（可以省略）

        // 2. 获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");

        // 3. 预编译SQL语句得到PreparedStatement
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT COUNT(*) as count FROM user");

        // 4. 执行SQL语句，获取结果
        ResultSet resultSet = prepareStatement.executeQuery();

        // 5. 处理结果(如果自己明确一定只有一个结果，那么resultSet最少要做一次next的判断，才能拿到我们要的列的结果)
//        while (resultSet.next()) {
//            String count = resultSet.getString("count");
//            System.out.println(count);
//        }
        if(resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        // 6. 释放资源
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testQuerySingleRow() throws Exception {
        // 1. 注册驱动

        // 2. 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");

        // 3. 预编译SQL语句获得PreparedStatement对象
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id,name,salary,age FROM user WHERE id = ?");

        // 4. 执行，并接受结果
        prepareStatement.setInt(1, 1);
        ResultSet resultSet = prepareStatement.executeQuery();

        // 5. 处理结果
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            int age = resultSet.getInt("age");
        }

        // 6. 资源释放
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testQueryMoreRow() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id,name,salary,age FROM user WHERE age > ?");
        prepareStatement.setInt(1, 25);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            int age = resultSet.getInt("age");
        }
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testInsert() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO user(name,salary,age) VALUES (?,?,?)");
        prepareStatement.setString(1, "ccc");
        prepareStatement.setDouble(2, 22.22);
        prepareStatement.setInt(3, 25);
        int i = prepareStatement.executeUpdate();
        if(i > 0) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testUpdate() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc-learn", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("UPDATE user SET salary = ? WHERE id = ?");
        prepareStatement.setDouble(1, 33.33);
        prepareStatement.setInt(1, 2);
        int i = prepareStatement.executeUpdate();
        if(i > 0) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testDelete() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc-learn", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        prepareStatement.setDouble(1, 1);
        int i = prepareStatement.executeUpdate();
        if(i > 0) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testT() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id,name,salary,age FROM user WHERE name = ?");
        prepareStatement.setString(1, "ccc");
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int id = resultSet.getInt("id");
            double salary = resultSet.getDouble("salary");
            int age = resultSet.getInt("age");
        }
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }
}
