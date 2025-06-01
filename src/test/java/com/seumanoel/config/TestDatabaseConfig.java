package com.seumanoel.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.UUID;

@TestConfiguration
public class TestDatabaseConfig {
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

}
