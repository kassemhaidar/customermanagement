package com.codechallenge.customermanagement.business.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for mobile number validation.
 */
@Getter
@Setter
@NoArgsConstructor
public class MobileValidationDto {

    /**
     * The carrier of the mobile number.
     */
    private String carrier;

    /**
     * The country code of the mobile number.
     */
    private String country_code;

    /**
     * The country name of the mobile number.
     */
    private String country_name;

    /**
     * The country prefix of the mobile number.
     */
    private String country_prefix;

    /**
     * The international format of the mobile number.
     */
    private String international_format;

    /**
     * The line type of the mobile number.
     */
    private String line_type;

    /**
     * The local format of the mobile number.
     */
    private String local_format;

    /**
     * The location associated with the mobile number.
     */
    private String location;

    /**
     * The mobile number.
     */
    private String number;

    /**
     * Indicates whether the mobile number is valid.
     */
    private boolean valid;
}

