package org.example;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class MultitenantDataSource extends AbstractRoutingDataSource {
    private final String defaultTenant;

    public MultitenantDataSource(String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    protected String determineCurrentLookupKey() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            Object tenant = attributes.getAttribute(TenantFilter.TENANT, RequestAttributes.SCOPE_REQUEST);
            if (Objects.nonNull(tenant) && tenant instanceof String) {
                return tenant.toString();
            }
        }

        return defaultTenant;
    }
}
