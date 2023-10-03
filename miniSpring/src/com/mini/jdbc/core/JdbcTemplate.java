package com.mini.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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

    public Object query(String sql,Object[] args,PreparedStatementCallback preparedStatementCallback){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = dataSource.getConnection();
            preparedStatement=  con.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(preparedStatement);
            return preparedStatementCallback.doInPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public <T> List<T> query(String sql,Object[] args,RowMapper<T> rowMapper){
        RowMapperResultSetExtractor resultSetExtractor = new RowMapperResultSetExtractor(rowMapper);

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(sql);

            ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(preparedStatement);
            rs = preparedStatement.executeQuery();
            return resultSetExtractor.extractData(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }







    }

}
