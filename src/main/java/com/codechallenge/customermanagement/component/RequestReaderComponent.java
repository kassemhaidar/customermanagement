package com.codechallenge.customermanagement.component;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.constant.Constants;

@Component
public class RequestReaderComponent {

    private static final Logger log = LoggerFactory.getLogger(RequestReaderComponent.class);

    private static HttpServletRequest getCurrentHttpRequest() {
        log.trace("Fetching current HTTP request");
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            log.trace("Current HTTP request: {}", request);
            return request;
        }
        log.warn("No current HTTP request found");
        return null;
    }

    /**
     * Returns the language from the request header with required = true.
     *
     * @return the language from the request header or the default language if not found
     * @throws CustomException if the header is required and not found
     */
    public String getLanguage() throws CustomException {
        log.trace("Getting language with required = false");
        return getLanguage(false);
    }

    /**
     * Returns the language from the request header.
     * If required is true, the function will throw an exception if the appropriate header key is not found.
     * If the appropriate header key is not found and required = false, then the default language (EN) is returned.
     *
     * @param required whether the header is required
     * @return the language from the request header or the default language if not found
     * @throws CustomException if the header is required and not found
     */
    public String getLanguage(Boolean required) throws CustomException {
        log.trace("Getting language with required = {}", required);
        HttpServletRequest request = getCurrentHttpRequest();
        if (request == null) {
            log.warn("No current HTTP request available to fetch the language");
            throw new CustomException(Constants.MISSING_REQUIRED_HEADER_ERROR);
        }

        String requestLanguage = request.getHeader(Constants.CONTENT_LANGUAGE_HEADER_KEY.toLowerCase());
        log.debug("Request language header: {}", requestLanguage);

        if (StringUtils.isAllBlank(requestLanguage)) {
            requestLanguage = Constants.DEFAULT_LANGUAGE;
            log.debug("Using default language: {}", requestLanguage);
            if (required) {
                log.error("Required language header is missing");
                throw new CustomException(Constants.MISSING_REQUIRED_HEADER_ERROR);
            }
        }

        log.debug("Returning language: {}", requestLanguage);
        return requestLanguage;
    }
}
