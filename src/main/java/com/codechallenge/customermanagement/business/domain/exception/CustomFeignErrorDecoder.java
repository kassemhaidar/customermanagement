package com.codechallenge.customermanagement.business.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.exception.CustomFeignErrorDecoder;
import com.codechallenge.customermanagement.business.service.MessageService;
import com.codechallenge.customermanagement.component.RequestReaderComponent;

import feign.Response;
import feign.codec.ErrorDecoder;
import javassist.NotFoundException;

/**
 * Custom error decoder for handling Feign client errors.
 * It decodes the response and throws specific exceptions based on the HTTP status code.
 * 
 * TODO CustomFeignErrorDecoder should be moved to a common library and not implemented in every project.
 */
public class CustomFeignErrorDecoder implements ErrorDecoder {
	
	@Autowired
	MessageService messageService;
	@Autowired
	RequestReaderComponent requestReaderComponent;
    
    private static final Logger log = LoggerFactory.getLogger(CustomFeignErrorDecoder.class);

	/**
     * Decodes the Feign client response and throws specific exceptions based on the status code.
     *
     * @param methodKey the Feign client method key
     * @param response  the Feign client response
     * @return an exception based on the status code
     */
    @Override
    public Exception decode(String methodKey, Response response) {
    	log.error("in CustomFeignErrorDecoder.decode() >>> ");
    	log.debug("Decoding error for methodKey: {}", methodKey);
        log.trace("Response status: {}, headers: {}", response.status(), response.headers());
        String errorMessage = "";
        // Handle specific HTTP error codes returned by the Feign client
    	try {
	    	switch (response.status()) {
	            case 400:
	                log.debug("Handling 400 error for methodKey: {}", methodKey);
	                errorMessage = messageService.getMessage("expected.error.occurred", null, requestReaderComponent.getLanguage());
	                log.trace("Returning error message: {}", errorMessage);
	                return new CustomException(errorMessage);
	            case 401:
	                log.debug("Handling 401 error for methodKey: {}", methodKey);
	                errorMessage = messageService.getMessage("unauthorized", null, requestReaderComponent.getLanguage());
	                log.trace("Returning error message: {}", errorMessage);
	                return new CustomException(errorMessage);
	            case 404:
	                log.debug("Handling 404 error for methodKey: {}", methodKey);
	                errorMessage = messageService.getMessage("api.not.found", null, requestReaderComponent.getLanguage());
	                log.trace("Returning error message: {}", errorMessage);
	                return new NotFoundException(errorMessage);
	            default:
	                log.debug("Handling default error for methodKey: {}", methodKey);
	                log.trace("Returning error message: {}", errorMessage);
	                return new RuntimeException(errorMessage);
	        }
    	} catch (CustomException e) {
    		log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
    }
}

