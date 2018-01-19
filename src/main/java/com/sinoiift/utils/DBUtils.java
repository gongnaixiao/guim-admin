package com.sinoiift.utils;

import com.sinoiift.domain.App;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DatabaseDriver;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by xg on 2018/1/18.
 */
public class DBUtils {

    public static DataSource createDataSource(App app) {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl("jdbc:oracle:thin:@" + app.getDbUrl());
        properties.setUsername(app.getDbUsername());
        properties.setPassword(app.getDbPassword());
        properties.setDriverClassName("oracle.jdbc.driver.OracleDriver");

        DataSource dataSource = (DataSource) properties.initializeDataSourceBuilder().type(DataSource.class).build();
        DatabaseDriver databaseDriver = DatabaseDriver
                .fromJdbcUrl(properties.determineUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }
        return dataSource;
    }

    public static void executeSql(String sqlFile, Connection conn) throws SQLException, FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setAutoCommit(true);//自动提交
        runner.setFullLineDelimiter(false);
        runner.setDelimiter(";");////每条命令间的分隔符
        runner.setSendFullScript(false);
        runner.setStopOnError(false);
        runner.setLogWriter(new PrintWriter(System.out));//设置是否输出日志
        runner.runScript(new InputStreamReader(new FileInputStream(sqlFile)));
    }

    public static Connection createConnection(App app) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = app.getDbUrl();
        String username = app.getDbUsername();
        String password = app.getDbPassword();
        return DriverManager.getConnection(url, username, password);
    }
}
