package com.wll.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author wulele
 * <p>
 * 操作数据库公共类
 */
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        Properties properties = new Properties();
        //通过类加载器读取资源
        InputStream rs = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        //反射获取对象
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    /**
     * 查询公共类
     */
    public static PreparedStatement execute(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //preparedStatement占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    /**
     * 增删改共类
     */
    public static PreparedStatement executeUpdate(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //preparedStatement占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    /**
     * 关闭资源
     */
    public static boolean closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;
        if (resultSet != null) {
            try {
                resultSet.close();
                //GC回收
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if(connection != null){
            try {
                connection.close();
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
