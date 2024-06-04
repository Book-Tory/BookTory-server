package com.booktory.booktoryserver.users.handler;

import com.booktory.booktoryserver.users.constant.Gender;
import com.booktory.booktoryserver.users.constant.Role;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Role.class)
public class GenderTypeHandler implements TypeHandler<Gender> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getKey());
    }

    @Override
    public Gender getResult(ResultSet rs, String columnName) throws SQLException {
        String genderKey = rs.getString(columnName);
        return getGender(genderKey);
    }

    @Override
    public Gender getResult(ResultSet rs, int columnIndex) throws SQLException {
        String genderKey = rs.getString(columnIndex);
        return getGender(genderKey);
    }

    @Override
    public Gender getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String genderKey = cs.getString(columnIndex);
        return getGender(genderKey);
    }

    private Gender getGender(String roleKey){
        Gender gender = null;
        switch (roleKey){
            case "MALE":
                gender = Gender.MALE;
                break;
            case "FEMALE":
                gender = Gender.FEMALE;
                break;
            case "OTHER":
                gender = Gender.OTHER;
                break;
            default:
                gender = Gender.MALE;
                break;
        }

        return gender;
    }

}
