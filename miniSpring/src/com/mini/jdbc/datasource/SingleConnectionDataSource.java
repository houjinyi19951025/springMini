package com.mini.jdbc.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class SingleConnectionDataSource implements DataSource {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private Properties connectionProperties;

    private Connection connection;

    public SingleConnectionDataSource() {
    }


    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        try {
            Class.forName(this.driverClassName);
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not load JDBC driver class [" + driverClassName + "]", ex);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(getUsername(),getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Properties mergeProperties = new Properties();
        Properties connProps = getConnectionProperties();
        if (connProps != null) {
            mergeProperties.putAll(connProps);
        }
        if (username != null) {
            mergeProperties.setProperty("user", username);
        }
        if (password != null) {
            mergeProperties.setProperty("password", password);
        }

        this.connection = getConnectionFromDriverManager(getUrl(),mergeProperties);

        return this.connection;
    }


    public Connection getConnectionFromDriverManager(String url,Properties properties) throws SQLException {

        return DriverManager.getConnection(url,properties);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
