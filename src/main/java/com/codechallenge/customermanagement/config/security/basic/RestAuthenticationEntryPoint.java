package com.codechallenge.customermanagement.config.security.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.codechallenge.customermanagement.business.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Custom AuthenticationEntryPoint implementation to handle unauthorized access.
 * 
 * TODO RestAuthenticationEntryPoint should be moved to a common library and not implemented in every project.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
    private MessageService messageService;

    @Autowired
    private LocaleResolver localeResolver;
    
	/**
     * Handles unauthorized access by sending an HTTP 401 Unauthorized error response.
     *
     * @param request the HttpServletRequest that resulted in an AuthenticationException
     * @param response the HttpServletResponse so that the user agent can be advised of the failure
     * @param authException the authentication exception that caused the invocation
     * @throws IOException if an input or output error occurs while the servlet is handling the request
     * @throws ServletException if the request for the POST could not be handled
     */
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Locale locale = localeResolver.resolveLocale(request);
        String unauthorizedMessage = messageService.getMessage("unauthorized", null, locale);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unauthorizedMessage);
   
    }
}
