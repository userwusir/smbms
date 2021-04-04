package com.wll.dao.role;

import com.wll.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wulele
 */
public interface RoleDao {
    /**
     * get role list
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
