package com.mini.batis;

import com.mini.jdbc.core.JdbcTemplate;
import com.mini.jdbc.core.PreparedStatementCallback;

public class DefaultSqlSession implements SqlSession{
    JdbcTemplate jdbcTemplate;

    SqlSessionFactory sqlSessionFactory;
    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        System.out.println("..................selectOne................."+sqlId);
        String sql = sqlSessionFactory.getMapperNode(sqlId).getSql();
        System.out.println("..................sql"+sql);

        return jdbcTemplate.query(sql,args,preparedStatementCallback);
    }
}
