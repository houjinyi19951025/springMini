package com.mini.jdbc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class OldJdbcTemplate {

    public OldJdbcTemplate() {
    }

    public Object query(String sql){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object rtnObj = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","root");

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            rtnObj = doInStatement(rs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {

            }
        }

        return rtnObj;

    }

    public abstract Object doInStatement(ResultSet rs) ;

}
