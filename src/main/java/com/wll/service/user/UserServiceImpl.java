package com.wll.service.user;

import com.wll.dao.BaseDao;
import com.wll.dao.user.UserDao;
import com.wll.dao.user.UserDaoImpl;
import com.wll.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wulele
 */
public class UserServiceImpl implements UserService {
    /**
     * 业务层调用Dao层
     */
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User userInfo = null;
        try {
            connection = BaseDao.getConnection();
            userInfo = userDao.getUserInfo(connection, userCode);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, null, null);
        }
        if (userCode.equals(userInfo.getUserCode()) && password.equals(userInfo.getUserPassword())) {
            return userInfo;
        } else {
            return null;
        }
    }

    @Override
    public boolean alertPsw(int id, String password) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            if (userDao.alertPsw(connection, id, password) > 0) {
                flag = true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, null, null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String userName, int userRole) {
        int count = 0;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, userName, userRole);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, null, null);
        }
        return count;
    }

    @Override
    public List<User> getUserinfoList(String userName, int userRole, int currentPageNo, int pageSize) {
        List<User> userList = new ArrayList<User>();
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserinfoList(connection, userName, userRole, currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, null, null);
        }
        return userList;
    }

    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        List<User> user = userService.getUserinfoList(null,0,1,5);
        System.out.println(user);
    }
}
