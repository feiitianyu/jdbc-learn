package com.jdbc.senior.dao.impl;

import com.jdbc.senior.dao.BaseDAO;
import com.jdbc.senior.dao.EmployeeDao;
import com.jdbc.senior.pojo.Employee;

import java.util.List;

public class EmployeeDaoImpl extends BaseDAO implements EmployeeDao {
    @Override
    public List<Employee> searchAll() {
        try {
            String sql = "SELECT id,name,salary,age FROM user";
            return executeQuery(Employee.class, sql, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee searchById(Integer id) {
        try {
            String sql = "SELECT id,name,salary,age FROM user WHERE id = ?";
            return executeQueryBean(Employee.class, sql, 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Employee employee) {
        try {
            String sql = "INSERT INTO user(name,salary,age) VALUES(?,?,?)";
            return executeUpdate(sql, employee.getName(), employee.getSalary(), employee.getAge());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        try {
            String sql = "UPDATE user SET salary = ? salary WHERE id = ?";
            return executeUpdate(sql, employee.getSalary(), employee.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer id) {
        try {
            String sql = "delete from user where id = ?";
            return executeUpdate(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
