package com.mini.test.service;

import com.mini.beans.factory.annotation.Autowired;
import com.mini.jdbc.core.JdbcTemplate;
import com.mini.jdbc.core.OldJdbcTemplate;
import com.mini.jdbc.core.StatementCallback;
import com.mini.test.dto.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentServiceImpl {

   @Autowired
    JdbcTemplate jdbcTemplate;
    public void query(){
        StudentJdbcImpl studentJdbc = new StudentJdbcImpl();
        String sql = "select name from students where id = 2";
        Student student = (Student) studentJdbc.query(sql);
        System.out.println(student);

    }

    public  void select(){
        Student student = (Student) jdbcTemplate.query(new StatementCallback() {
            @Override
            public Student doInStatement(Statement stmt) throws SQLException {
                Student s = new Student();
                String sql = "select name from students where id = 4";
                ResultSet resultSet = stmt.executeQuery(sql);
                if(resultSet.next()){
                    s.name= resultSet.getString("name");
                }

                return s;
            }
        });
        System.out.println(student);

    }
}
