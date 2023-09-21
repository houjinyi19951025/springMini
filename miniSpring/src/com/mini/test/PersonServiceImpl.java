package com.mini.test;

import java.sql.*;

public class PersonServiceImpl {

    public void  say(){

        System.out.println("执行say，");
    }

    public void query(){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;
        String sql = "select name from students where id = 1";

        try {
            //加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 获得数据库链接
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","root");
            // 创建statement对象
            preparedStatement = con.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();
            System.out.println("将要输出数据库数据");
            if(resultSet.next()){
                System.out.println("学生表姓名"+resultSet.getString("name"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                con.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
