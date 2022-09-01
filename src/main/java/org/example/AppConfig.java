package org.example;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Bean
    public MultitenantDataSource dataSource(TenantDataSourceProperties properties) {
        Map<String, DataSourceProperties> dataSourceProperties = properties.getDatasources();
        Map<Object, Object> dataSources = dataSourceProperties.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> createDataSource(e.getValue())));

        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(createDataSource(properties.getDatasource()));
        dataSource.setTargetDataSources(dataSources);
        return dataSource;
    }

    private DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
