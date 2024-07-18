package com.codechallenge.customermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Configuration class to define a custom LocaleResolver based on the Accept-Language header.
 * TODO Configuration should be moved to a common library and not implemented in every project.
 */
@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver {

	/**
     * Resolve the locale based on the Accept-Language header of the incoming request.
     *
     * @param request the HttpServletRequest containing the Accept-Language header
     * @return the resolved Locale based on the Accept-Language header or the default locale if header is not present
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return (headerLang == null || headerLang.isEmpty())
                ? Locale.getDefault()
                : Locale.forLanguageTag(headerLang);
    }

    /**
     * Configure and return the LocaleResolver bean.
     *
     * @return the configured LocaleResolver bean
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleConfig();
    }
}

