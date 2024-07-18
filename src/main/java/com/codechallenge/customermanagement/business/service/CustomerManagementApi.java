package com.codechallenge.customermanagement.business.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.codechallenge.customermanagement.business.domain.dto.MobileValidationDto;
import com.codechallenge.customermanagement.business.domain.exception.CustomFeignErrorDecoder;

import feign.codec.ErrorDecoder;

/**
 * Feign client interface for mobile number validation.
 */
@FeignClient(value = "mobileValidation", url = "${mobile.validation.api.url}", configuration = CustomerManagementApi.CustomFeignConfiguration.class)
public interface CustomerManagementApi {
	
	/**
     * Validates a mobile number.
     *
     * @param number the mobile number to validate
     * @param apiKey the API key for authentication
     * @return the mobile validation result as a {@link MobileValidationDto}
     */
	@GetMapping(value = "/validate")
	MobileValidationDto validateNumber(@RequestParam(name = "number") String number, 
			@RequestHeader("Authorization") String basicAuth);
	
	// Custom Feign configuration class for error handling
    class CustomFeignConfiguration {
        public ErrorDecoder errorDecoder() {
            return new CustomFeignErrorDecoder();
        }
    }
}