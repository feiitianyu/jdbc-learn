package com.jdbc.senior.dao;

import com.jdbc.senior.pojo.Employee;

import java.util.List;

/**
 * EmployeeDao这个类对应的是emp这张表的增删改查的操作
 */

public interface EmployeeDao {
    /**
     * 数据库对应的查询所以的操作
     * @return 表中所有数据
     */
    List<Employee> searchAll();

    /**
     *  数据库对应的根据id查询单个员工数据操作
     * @param id 主键列
     * @retuen 一个员工对象（一行数据）
     */
    Employee searchById(Integer id);

    /**
     *  数据库对应的新增一条员工数据
     * @param employee ORM思想中的一个员工对象
     * @return 受影响的行数
     */
    int insert(Employee employee);

    /**
     * 数据库对应的修改一条员工的数据
     * @param employee ORM思想中的一个员工对象
     * @return 受影响的行数
     */
    int update(Employee employee);

    /**
     * 数据库对应的根据id删除一条员工数据
     * @param id 主键列
     * @return 受影响的行数
     */
    int delete(Integer id);
}
