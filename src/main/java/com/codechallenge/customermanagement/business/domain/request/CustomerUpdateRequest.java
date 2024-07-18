package com.codechallenge.customermanagement.business.domain.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request object for updating a customer.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerUpdateRequest extends CustomerRequest implements Serializable{
	
	private static final long serialVersionUID = -6993053799401442218L;

	private Long id;

	/**
	 * Constructs a new CustomerUpdateRequest with the specified name, address, and mobile number.
	 *
	 * @param name the name of the customer
	 * @param address the address of the customer
	 * @param mobileNumber the mobile number of the customer
	 */
	public CustomerUpdateRequest(String name, String address, String mobileNumber) {
		super(name, address, mobileNumber);
	}

}
