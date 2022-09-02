package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class TenantFilter extends OncePerRequestFilter {
    @Value("${default.tenant}")
    private String defaultTenant;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            String tenant = req.getHeader("X-TENANT-ID");
            if (Objects.isNull(tenant)) {
                tenant = defaultTenant;
            }

            attributes.setAttribute("tenant", tenant, RequestAttributes.SCOPE_REQUEST);
            RequestContextHolder.setRequestAttributes(attributes, true);
        }
        chain.doFilter(req, res);
    }
}
