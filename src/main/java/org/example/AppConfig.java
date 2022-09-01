package org.example;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Bean("dataSource")
    public MultitenantDataSource dataSource(TenantDataSourceProperties properties, Map<Object, Object> dataSources) {
        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(createDataSource(properties.getDatasource()));
        dataSource.setTargetDataSources(dataSources);
        return dataSource;
    }

    @Bean
    public Map<Object, Object> dataSources(TenantDataSourceProperties properties) {
        Map<String, DataSourceProperties> dataSourceProperties = properties.getDatasources();
        return dataSourceProperties.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> createDataSource(e.getValue())));
    }

    private DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
