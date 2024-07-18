package com.codechallenge.customermanagement.business.service;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.response.MobileValidationResponse;

public interface CustomerManagementService {
    /**
     * Validate a mobile number.
     *
     * @param number the mobile number to validate
     * @return mobileValidationResponse containing countryCode, countryName, operatorName, mobileNumber and valid {@link MobileValidationResponse}
     * @throws CustomException if there is an error during validation
     */
    MobileValidationResponse validateMobileNumber(String number) throws CustomException;
}

