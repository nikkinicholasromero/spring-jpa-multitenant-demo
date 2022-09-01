package org.example;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application.multitenant")
public class TenantDataSourceProperties {

    private DataSourceProperties datasource;

    private Map<String, DataSourceProperties> datasources;

    public DataSourceProperties getDatasource() {
        return datasource;
    }

    public Map<String, DataSourceProperties> getDatasources() {
        return datasources;
    }

    public void setDatasource(DataSourceProperties datasource) {
        this.datasource = datasource;
    }

    public void setDatasources(Map<String, DataSourceProperties> datasources) {
        this.datasources = datasources;
    }

}
