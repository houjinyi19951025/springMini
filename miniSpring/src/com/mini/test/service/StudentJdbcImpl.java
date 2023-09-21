package com.mini.test.service;

import com.mini.jdbc.core.OldJdbcTemplate;
import com.mini.test.dto.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentJdbcImpl extends OldJdbcTemplate {
    @Override
    public Object doInStatement(ResultSet rs) {

        Student student =null;

        try {
            if(rs.next()){
                student = new Student();
                student.name = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }
}
