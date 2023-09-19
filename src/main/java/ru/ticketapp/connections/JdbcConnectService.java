package ru.ticketapp.connections;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcConnectService {

    public static final String JDBC_URL="jdbc:mysql://cat.world:3306/stmlabs";
    public static final String JDBC_USERNAME="test";
    public static final String JDBC_PASSWORD="test";
    public static final String JDBC_DRIVER="org.mysql.jdbc.Driver";

    public static JdbcTemplate getTemplate() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(JDBC_DRIVER);

        dataSource.setUrl(JDBC_URL);

        dataSource.setUsername(JDBC_USERNAME);

        dataSource.setPassword(JDBC_PASSWORD);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;
    }
}
