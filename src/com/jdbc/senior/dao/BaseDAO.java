package com.jdbc.senior.dao;

import com.jdbc.senior.util.JDBCUtilV2;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 将共性的数据库的操作代码封装在BaseDAO里
 */

public class BaseDAO {
    /**
     * 通用的增删改的方法
     * @param sql 调用者要执行的SQL语句
     * @param params SQL语句中的占位符要赋值的参数
     * @return 受影响的行数
     */
    public int executeUpdate(String sql,Object... params) throws Exception {
        // 1. 通过JDBCUtilV2获取数据库连接
        Connection connection = JDBCUtilV2.getConnection();

        // 2. 预编译SQL语句
        PreparedStatement prepareStatement = connection.prepareStatement(sql);

        // 4. 为占位符赋值，执行SQL，接受返回结果
        if(params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                // 占位符是从1开始的，参数的数组是从0开始的
                prepareStatement.setObject(i+1, params[i]);
            }
        }
        int i = prepareStatement.executeUpdate();

        // 6. 释放资源
        prepareStatement.close();
        if(connection.getAutoCommit()) {
            JDBCUtilV2.release();
        }
        // 5. 处理结果
        return i;
    }

    /**
     * 通用的查询：多行多列，单行多列，单行单列
     *      多行多列：List<Employee>
     *      单行多列：Employee
     *      单行单列：封装的是一个结果。Double,Integer ......
     *  封装的过程：
     *      1. 返回的类型：泛型：类型不确定，调用者知道，调用时，将此次查询的结果类型告知BaseDAO就可以了
     *      2. 返回的结果：通用：List    可以存储多个结果，也可以存储一个结果 get(0)
     *      3. 结果的封装：反射，要求调用者告知BaseDAO要封装对象的类对象。Class
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql,Object... params) throws Exception {
        // 获取连接
        Connection connection = JDBCUtilV2.getConnection();

        // 预编译SQL语句
        PreparedStatement prepareStatement = connection.prepareStatement(sql);

        // 设置占位符的值
        if(params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                prepareStatement.setObject(i+1, params[i]);
            }
        }

        // 执行SQL，并接受返回的结果集
        ResultSet resultSet = prepareStatement.executeQuery();

        // 获取结果集中的元数据对象
        // 包含了：列的数量，每个列的名称
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        ArrayList<T> list = new ArrayList<>();
        // 处理结果
        while (resultSet.next()) {
            // 循环一次，代表有一行数据，通过反射创建一个对象
            T t = clazz.newInstance();
            // 循环遍历当前行的列，循环几次，看有多少列
            for (int i = 0; i < columnCount; i++) {
                // 通过下表获取列的值
                Object object = resultSet.getObject(i + 1);

                // 获取到的列的value的值，这个值就是t这个对象中的某一个属性
                // 获取当前拿到的列的名字 = 对象的属性名
                String columnLabel = metaData.getColumnLabel(i);
                // 通过类对象和fieldName获取要封装的对象的属性
                Field declaredField = clazz.getDeclaredField(columnLabel);
                // 突破封装的private
                declaredField.setAccessible(true);
                declaredField.set(t, object);
            }

            list.add(t);
        }

        resultSet.close();
        prepareStatement.close();
        if(connection.getAutoCommit()) {
            JDBCUtilV2.release();
        }

        return list;
    }

    /**
     * 通用查询：在上面查询的集合结果中获取第一个结果。简化了获取单行单列的获取，单行多列的获取
     */
    public <T> T executeQueryBean(Class<T> clazz, String sql,Object... params) throws Exception {
        List<T> list = this.executeQuery(clazz, sql, params);
        if(list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
