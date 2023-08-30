package com.card.management.configuration.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    @Value("${DB_USERNAME}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    @Value("${DB_URL}")
    private String url;

    @Bean
    @Primary
    @Lazy
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder.
                create()
                .password(password)
                .username(username)
                .driverClassName("com.mysql.jdbc.Driver")
                .url(url).
                type(HikariDataSource.class).
                build();
    }
}
