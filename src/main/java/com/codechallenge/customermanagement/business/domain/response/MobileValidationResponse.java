package com.codechallenge.customermanagement.business.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response object for mobile number validation.
 */
@Getter
@Setter
@NoArgsConstructor
public class MobileValidationResponse {

    /**
     * The country code of the validated mobile number.
     */
    private String countryCode;

    /**
     * The country name of the validated mobile number.
     */
    private String countryName;

    /**
     * The operator name of the validated mobile number.
     */
    private String operatorName;

    /**
     * The validated mobile number.
     */
    private String mobileNumber;

    /**
     * Indicates whether the mobile number is valid.
     */
    private boolean valid;

    /**
     * Constructs a new MobileValidationResponse with the specified details.
     *
     * @param countryCode the country code of the validated mobile number
     * @param countryName the country name of the validated mobile number
     * @param operatorName the operator name of the validated mobile number
     * @param mobileNumber the validated mobile number
     * @param valid indicates whether the mobile number is valid
     */
    public MobileValidationResponse(String countryCode, String countryName, String operatorName, String mobileNumber,
            boolean valid) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.operatorName = operatorName;
        this.mobileNumber = mobileNumber;
        this.valid = valid;
    }
}