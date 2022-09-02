package org.example;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class MultitenantDataSource extends AbstractRoutingDataSource {
    private final String defaultTenant;

    public MultitenantDataSource(String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    protected String determineCurrentLookupKey() {
        try {
            return RequestContextHolder.getRequestAttributes()
                    .getAttribute("tenant", RequestAttributes.SCOPE_REQUEST).toString();
        } catch (Exception e) {
            return defaultTenant;
        }
    }
}
