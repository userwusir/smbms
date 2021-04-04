package com.wll.service.user;

import com.wll.pojo.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wulele
 */
public interface UserService {
    /**
     * method for user login.
     * @param userCode
     * @param password
     * @return
     */
    public User login(String userCode,String password);

    /**
     * alert user password
     * @param id
     * @param password
     * @return
     */
    public boolean alertPsw(int id, String password);

    /**
     * get user count
     * @param userName
     * @param userRole
     * @return
     */
    public int getUserCount(String userName,int userRole);

    /**
     * get userinfo list
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<User> getUserinfoList(String userName, int userRole, int currentPageNo, int pageSize);
}
