package com.wll.dao.user;

import com.mysql.cj.util.StringUtils;
import com.wll.dao.BaseDao;
import com.wll.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wulele
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User getUserInfo(Connection connection, String userCode) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        if (connection != null) {
            String sql = "select * from smbms_user where userCode = ?";
            Object[] params = {userCode};
            preparedStatement = BaseDao.execute(connection, sql, params, preparedStatement, resultSet);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResources(null, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public int alertPsw(Connection connection, int id, String userPassword) throws SQLException {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {userPassword, id};
            preparedStatement = BaseDao.executeUpdate(connection, sql, params, preparedStatement);
            rows = preparedStatement.executeUpdate();
            BaseDao.closeResources(null, preparedStatement, null);
        }
        return rows;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            List<Object> list = new ArrayList<Object>();
            sql.append("select count(1) as count from smbms_user a,smbms_role b where a.userRole = b.id");
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and a.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and a.userRole = ?");
                list.add(userRole);
            }
            Object[] params = list.toArray();
            preparedStatement = BaseDao.execute(connection, sql.toString(), params, preparedStatement, resultSet);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            BaseDao.closeResources(null, preparedStatement, resultSet);
        }
        return count;
    }

    @Override
    public List<User> getUserinfoList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            ArrayList<Object> list = new ArrayList<>();
            sql.append("select a.*,b.roleName as userRoleName from smbms_user a,smbms_role b where a.userRole = b.id");
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and a.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and a.userName = ?");
                list.add(userRole);
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            preparedStatement = BaseDao.execute(connection, sql.toString(), params, preparedStatement, resultSet);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
                userList.add(user);
            }
            BaseDao.closeResources(null, preparedStatement, resultSet);
        }
        return userList;
    }
}
