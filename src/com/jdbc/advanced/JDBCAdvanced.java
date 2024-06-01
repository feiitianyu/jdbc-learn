package com.jdbc.advanced;

import com.jdbc.advanced.pojo.Employee;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAdvanced {
    @Test
    public void testORM() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id,name,salary,age FROM user WHERE id = ?");
        prepareStatement.setInt(1, 1);
        ResultSet resultSet = prepareStatement.executeQuery();
        Employee employee = null;
        if(resultSet.next()) {
            employee = new Employee();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            int age = resultSet.getInt("age");
            employee.setId(id);
            employee.setName(name);
            employee.setSalary(salary);
            employee.setAge(age);
        }
        System.out.println(employee);
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testORMList() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id,name,salary,age FROM user");
        ResultSet resultSet = prepareStatement.executeQuery();
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            employee = new Employee();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            int age = resultSet.getInt("age");
            employee.setId(id);
            employee.setName(name);
            employee.setSalary(salary);
            employee.setAge(age);
            employeeList.add(employee);
        }

        for (Employee emp : employeeList) {
            System.out.println(emp);
        }
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testORMReturnPK() throws Exception {
        // 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");

        // 预编译SQL语句，告知prepareStatement，返回新增数据的主键列的值
        String sql = "INSERT INTO user(name,salary,age) VALUES(?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // 创建对象，将对象的属性值，填充在?占位符上（ORM）
        Employee employee = new Employee(null, "ccc", 23.23, 24);
        prepareStatement.setString(1, employee.getName());
        prepareStatement.setDouble(2, employee.getSalary());
        prepareStatement.setInt(3, employee.getAge());

        // 执行SQL，并获取返回的结果
        int i = prepareStatement.executeUpdate();

        ResultSet generatedKeys = null;

        // 处理结果
        if(i > 0) {
            System.out.println("成功");

            // 获取当前新增数据的主键列，回显到Java中employee对象的id属性上
            // 返回的主键值，是一个单行单列的结果存储在resultSet里
            generatedKeys = prepareStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                employee.setId(id);
            }
        } else  {
            System.out.println("失败");
        }

        // 释放资源
        if(generatedKeys != null) {
            generatedKeys.close();
        }
        prepareStatement.close();
        connection.close();

    }

    @Test
    public void testMoreInsert() throws Exception {
        // 1. 注册驱动
//        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");

        // 3. 编写SQL语句
        String sql = "INSERT INTO user(name,salary,age) VALUES(?,?,?)";

        // 4. 创建预编译的PreparedStatement，传入SQL语句
        PreparedStatement prepareStatement = connection.prepareStatement(sql);

        // 获取当前行代码执行的时间。毫秒值
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // 5. 为占位符赋值
            prepareStatement.setString(1, "ccc" + i);
            prepareStatement.setDouble(2, 100.0 + i);
            prepareStatement.setInt(3, 12 + i);
            prepareStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));

        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testBatch() throws Exception {
        // 1. 注册驱动
//        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc?rewiriteBatchedStatements=true", "root", "123456");

        // 3. 编写SQL语句
        /*
            注意：1. 必须在连接数据库的URL后面追加?rewiriteBatchedStatements=true，允许批量操作
                2. 新增SQL必须用values，且语句最后不要追加;结束
                3. 调用addBatch方法，将SQL语句进行批量添加操作
         */
        String sql = "INSERT INTO user(name,salary,age) VALUES(?,?,?)";

        // 4. 创建预编译的PreparedStatement，传入SQL语句
        PreparedStatement prepareStatement = connection.prepareStatement(sql);

        // 获取当前行代码执行的时间。毫秒值
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // 5. 为占位符赋值
            prepareStatement.setString(1, "ccc" + i);
            prepareStatement.setDouble(2, 100.0 + i);
            prepareStatement.setInt(3, 12 + i);
            prepareStatement.addBatch();
        }

        // 执行批量操作
        prepareStatement.executeBatch();

        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));

        prepareStatement.close();
        connection.close();
    }
}
