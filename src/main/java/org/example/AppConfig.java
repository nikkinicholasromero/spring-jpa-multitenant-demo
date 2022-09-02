package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Value("${default.tenant}")
    private String defaultTenant;

    @Bean
    public MultitenantDataSource dataSource(TenantDataSourceProperties properties) {
        MultitenantDataSource dataSource = new MultitenantDataSource(defaultTenant);
        dataSource.setDefaultTargetDataSource(createDataSource(properties.getDatasources().get(defaultTenant)));
        dataSource.setTargetDataSources(properties.getDatasources().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> createDataSource(e.getValue()))));
        return dataSource;
    }

    private DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
