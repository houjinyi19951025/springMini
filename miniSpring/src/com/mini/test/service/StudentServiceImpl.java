package com.mini.test.service;

import com.mini.beans.factory.annotation.Autowired;
import com.mini.jdbc.core.JdbcTemplate;
import com.mini.jdbc.core.PreparedStatementCallback;
import com.mini.jdbc.core.RowMapper;
import com.mini.jdbc.core.StatementCallback;
import com.mini.test.dto.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    public Student selectByArgs(int id){
        String sql = "select name from students where id = ?";
      return   (Student)jdbcTemplate.query(sql, new Object[]{new Integer(id)}, new PreparedStatementCallback() {
            @Override
            public Student doInPreparedStatement(PreparedStatement stmt) throws SQLException {
                Student student = new Student();
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()){
                    student.name = resultSet.getString("name");
                }
                System.out.println("带参数的学生："+student);
                return student;
            }



        });
    }

    public List<Student> queryList(int id ){

        String sql = "select name from students where id >?";
       return jdbcTemplate.query(sql, new Object[]{new Integer(id)}, new RowMapper<Student>() {

           @Override
           public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
               Student student = new Student();
               student.name = rs.getString("name");
               return student;
           }
       });
    }
}
