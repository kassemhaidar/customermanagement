package com.codechallenge.customermanagement.config.security.basic;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Custom authentication entry point for handling authentication failure.
 * 
 * TODO CustomFilter should be moved to a common library and not implemented in every project
 */
public class CustomFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            filterChain.doFilter(servletRequest,servletResponse);
    }
}
