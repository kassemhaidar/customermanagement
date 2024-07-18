package com.codechallenge.customermanagement.business.domain.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request object for creating a customer.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerRequest implements Serializable {
	
	private static final long serialVersionUID = -6993053799401442218L;

	private String name;
	private String address;
	private String mobileNumber;

	/**
	 * Constructs a new CustomerRequest with the specified name, address, and mobile number.
	 *
	 * @param name the name of the customer
	 * @param address the address of the customer
	 * @param mobileNumber the mobile number of the customer
	 */
	public CustomerRequest(String name, String address, String mobileNumber) {
		this.name = name;
		this.address = address;
		this.mobileNumber = mobileNumber;
	}
}