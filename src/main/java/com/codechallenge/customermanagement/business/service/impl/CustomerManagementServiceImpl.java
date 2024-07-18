package com.codechallenge.customermanagement.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codechallenge.customermanagement.business.domain.dto.MobileValidationDto;
import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.response.MobileValidationResponse;
import com.codechallenge.customermanagement.business.service.CustomerManagementApi;
import com.codechallenge.customermanagement.business.service.CustomerManagementService;
import com.codechallenge.customermanagement.constant.Constants;
import com.codechallenge.customermanagement.business.service.MessageService;
import com.codechallenge.customermanagement.component.RequestReaderComponent;

import feign.FeignException;

/**
 * Service class for calling mobile validation API
 */
@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

    @Autowired
    private CustomerManagementApi mobileValidationInterface;

    @Value("${mobile.validation.basic.auth}")
    private String basicAuth;
    
    private static final Logger log = LoggerFactory.getLogger(CustomerManagementServiceImpl.class);
	
	@Autowired
	MessageService messageService;
	@Autowired
	RequestReaderComponent requestReaderComponent;
	
    /**
     * {@inheritDoc}
     */
    @Override
    public MobileValidationResponse validateMobileNumber(String number) throws CustomException {
    	log.info("in validateMobileNumber() before validation : " + number); 
    	try {
	    	MobileValidationDto response = mobileValidationInterface.validateNumber(number, basicAuth);
        	// TODO MobileValidationResponse.toString() to be readable response
	    	log.info("in validateMobileNumber() after validation : " + response.toString()); 
	        return new MobileValidationResponse(response.getCountry_code(), response.getCountry_name(), response.getCarrier(),response.getNumber(),response.isValid());
    	 } catch (Exception ex) {
    		 ex.printStackTrace();
    		 FeignException fe = (FeignException)ex;
 	    	 log.error("in validateMobileNumber() >>> status: " + fe.status() + ", message: " + fe.getMessage()); 
    		 if(fe.status() == 400 && fe.getMessage().contains(Constants.INVALID_MOBILE_NUMBER)) {
    			 throw new CustomException(
    					 messageService.getMessage("invalid.mobile.number",null,requestReaderComponent.getLanguage()));
    		 }
    		 if(fe.status() == 404) {
    			 throw new CustomException(
    					 messageService.getMessage("api.not.found",null,requestReaderComponent.getLanguage()));
    		 }
    		 if(fe.status() == 401) {
    			 throw new CustomException(
    					 messageService.getMessage("unauthorized",null,requestReaderComponent.getLanguage()));
    		 }
    		 throw new CustomException(
    				 messageService.getMessage("internal.server.error",null,requestReaderComponent.getLanguage()));
         }
    }
}

