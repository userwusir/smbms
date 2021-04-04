package com.wll.dao.role;

import com.wll.dao.BaseDao;
import com.wll.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wulele
 */
public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Role> roleList = new ArrayList<Role>();
        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            preparedStatement = BaseDao.execute(connection, sql, params, preparedStatement, resultSet);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("roleCode"));
                role.setRoleName(resultSet.getString("roleName"));
                roleList.add(role);
            }
            BaseDao.closeResources(null, preparedStatement, resultSet);
        }
        return roleList;
    }
}
