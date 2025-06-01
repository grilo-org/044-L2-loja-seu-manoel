package com.seumanoel.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class BaseTestConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void cleanDatabase() {
        // Ordem de limpeza importante por causa das chaves estrangeiras
        jdbcTemplate.execute("DELETE FROM app_user");
        // Adicione outras tabelas conforme necessário
    }
}
