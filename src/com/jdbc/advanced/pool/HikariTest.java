package com.jdbc.advanced.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariTest {
    @Test
    public void testHardCodeHikari() throws Exception {
        /*
            硬编码：将连接池的配置信息和Java代码耦合在一起
            1. 创建HikariDataSource连接池对象
            2. 设置连接池的配置信息【必须 | 非必须】
            3. 通过连接池获取连接对象
            4. 回收连接【不是释放连接，而是将连接归还给连接池，给其它线程进行复用】
         */

        // 1. 创建HikariDataSource连接池对象
        HikariDataSource hikariDataSource = new HikariDataSource();

        // 2. 设置连接池的配置信息【必须 | 非必须】
        // 2.1 必须设置的配置
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql:///jdbc-learn");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("123456");
        // 2.2 非必须设置的配置
        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setMaximumPoolSize(20);

        // 3. 通过连接池获取连接对象
        Connection connection = hikariDataSource.getConnection();

        // 4. 回收连接
        connection.close();
    }

    @Test
    public void testResourcesHikari() throws Exception {
        // 1. 创建Properties集合，用于存储外部配置文件的key和value的值
        Properties properties = new Properties();

        // 2. 读取外部配置文件，获取输入流，加载到Properties集合里
        InputStream inputStream = HikariTest.class.getClassLoader().getResourceAsStream("hikari.properties");
        properties.load(inputStream);

        // 3. 创建HikariConfig连接池配置对象，将Properties集合传进去
        HikariConfig hikariConfig = new HikariConfig(properties);

        // 4. 基于HikariConfig连接池配置对象，构建HikariDataSource
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        // 5. 获取连接
        Connection connection = hikariDataSource.getConnection();

        // 6. 回收连接
        connection.close();

    }
}
