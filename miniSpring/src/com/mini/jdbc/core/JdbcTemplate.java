package com.mini.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcTemplate {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public JdbcTemplate() {
    }
    public Object query(StatementCallback statementCallback){
        Connection con = null;
        Statement stmt = null;
        Object rtnObj = null;
        try {
            con = dataSource.getConnection();

            stmt = con.createStatement();

            rtnObj= statementCallback.doInStatement(stmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (Exception e) {

            }
        }

        return rtnObj;

    }


}
