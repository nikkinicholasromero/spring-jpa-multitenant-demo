package org.example;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute("tenant", req.getHeader("X-TENANT-ID"), RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(attributes, true);
        chain.doFilter(req, res);
    }
}
