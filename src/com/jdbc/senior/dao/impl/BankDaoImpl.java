package com.jdbc.senior.dao.impl;

import com.jdbc.senior.dao.BankDao;
import com.jdbc.senior.dao.BaseDAO;

public class BankDaoImpl extends BaseDAO implements BankDao {
    @Override
    public int addMoney(Integer id, Integer money) {
        try {
            String sql = "UPDATE bank SET money = money + ? WHERE id = ?";
            return executeUpdate(sql, money, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int subMonay(Integer id, Integer money) {
        try {
            String sql = "UPDATE bank SET money = money - ? WHERE id = ?";
            return executeUpdate(sql, money, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
