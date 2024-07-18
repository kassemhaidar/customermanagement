package com.codechallenge.customermanagement.business.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.service.MessageService;
import com.codechallenge.customermanagement.component.RequestReaderComponent;

/**
 * Global exception handler for handling custom exceptions in the application.
 * 
 * TODO CustomExceptionHandler should be moved to a common library and not implemented in every project.
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @Autowired
	MessageService messageService;
	@Autowired
	RequestReaderComponent requestReaderComponent;
	/**
     * Handles CustomException and returns an appropriate HTTP response.
     *
     * @param ex the CustomException to handle
     * @return a ResponseEntity with the appropriate HTTP status and error message
	 * @throws CustomException 
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) throws CustomException {
    	String errorMessage;
        HttpStatus status;
    	
    	log.error("in CustomExceptionHandler.handleCustomException() >>> ");
    	log.error("CustomException occurred: {}", ex.getMessage());
        log.debug("Handling CustomException: {}", ex);
        if (ex instanceof CustomException) {
        	log.trace("Handling CustomException instance");
            errorMessage = ex.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } else {
        	log.trace("Handling non-CustomException instance");
            errorMessage = messageService.getMessage("unexpected.error.occurred", null, requestReaderComponent.getLanguage()) + " : " + ex.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        log.debug("Returning ResponseEntity with status: {} and errorMessage: {}", status, errorMessage);
        return ResponseEntity.status(status).body(errorMessage);
    }
}
