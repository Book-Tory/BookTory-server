package com.booktory.booktoryserver.users.handler;

import com.booktory.booktoryserver.products_shop.constant.ProductStock;
import com.booktory.booktoryserver.users.constant.Role;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Role.class)
public class RoleTypeHandler implements TypeHandler<Role> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getKey());
    }

    @Override
    public Role getResult(ResultSet rs, String columnName) throws SQLException {
        String roleKey = rs.getString(columnName);
        return getRole(roleKey);
    }

    @Override
    public Role getResult(ResultSet rs, int columnIndex) throws SQLException {
        String roleKey = rs.getString(columnIndex);
        return getRole(roleKey);
    }

    @Override
    public Role getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String roleKey = cs.getString(columnIndex);
        return getRole(roleKey);
    }

    private Role getRole(String roleKey){
        Role role = null;
        switch (roleKey){
            case "ROLE_USER":
                role = Role.USER;
                break;
            case "ROLE_ADMIN":
                role = Role.ADMIN;
                break;
            default:
                role = Role.USER;
                break;
        }

        return role;
    }

}
