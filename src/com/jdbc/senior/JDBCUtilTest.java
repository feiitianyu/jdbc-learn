package com.jdbc.senior;

import com.jdbc.senior.dao.impl.BankDaoImpl;
import com.jdbc.senior.util.JDBCUtil;
import com.jdbc.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        // CRUD

        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCV2() {
//        Connection connection1 = JDBCUtil.getConnection();
//        Connection connection2 = JDBCUtil.getConnection();
//        Connection connection3 = JDBCUtil.getConnection();
//        System.out.println(connection1);
//        System.out.println(connection2);
//        System.out.println(connection3);

        Connection connection1 = JDBCUtil.getConnection();
        Connection connection2 = JDBCUtil.getConnection();
        Connection connection3 = JDBCUtil.getConnection();
        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);
    }

    @Test
    public void testEmployeeDao() {

    }

    @Test
    public void testTransaction() {
        BankDaoImpl bankDao = new BankDaoImpl();
        Connection connection = null;

        try {
            // 1. 获取连接，将连接的事务提交改为手动提交
            connection = JDBCUtilV2.getConnection();
            connection.setAutoCommit(false);

            // 2. 操作减钱
            bankDao.subMonay(1, 100);

            // 3. 操作加钱
            bankDao.addMoney(2, 100);

            // 4. 前置的多次dao操作，没有异常，提交事务！
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            JDBCUtilV2.release();
        }
    }
}
