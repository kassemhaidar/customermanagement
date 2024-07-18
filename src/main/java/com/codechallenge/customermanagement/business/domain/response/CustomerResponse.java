package com.codechallenge.customermanagement.business.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response object for retrieving customer information.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {

    /**
     * The unique identifier of the customer.
     */
    private Long id;

    /**
     * The name of the customer.
     */
    private String name;

    /**
     * The address of the customer.
     */
    private String address;

    /**
     * The mobile number of the customer.
     */
    private String mobileNumber;

}