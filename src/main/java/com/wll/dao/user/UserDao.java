package com.wll.dao.user;

import com.wll.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wulele
 */
public interface UserDao {
    /**
     * get user info.
     *
     * @param connection
     * @param userCode
     * @return User info
     * @throws SQLException getUserInfo
     */
    public User getUserInfo(Connection connection, String userCode) throws SQLException;

    /**
     * alert user password
     *
     * @param connection
     * @param id
     * @param userPassword
     * @return update sql rows
     * @throws SQLException
     */
    public int alertPsw(Connection connection, int id, String userPassword) throws SQLException;

    /**
     * get user count
     *
     * @param connection
     * @param userName
     * @param userRole
     * @return count
     * @throws SQLException
     */
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException;

    /**
     * get userinfo list
     *
     * @param connection
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @param pageSize
     * @return userinfo list
     * @throws Exception
     */
    public List<User> getUserinfoList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws Exception;
}
