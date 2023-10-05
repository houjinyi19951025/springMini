package com.mini.batis;

import com.mini.jdbc.core.JdbcTemplate;
import com.mini.jdbc.core.PreparedStatementCallback;

public interface SqlSession {

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    Object selectOne(String sqlId, Object[] args, PreparedStatementCallback preparedStatementCallback);


}
