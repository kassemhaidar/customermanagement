package com.codechallenge.customermanagement.business.service;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.request.CustomerRequest;
import com.codechallenge.customermanagement.business.domain.request.CustomerUpdateRequest;
import com.codechallenge.customermanagement.data.entity.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for managing customers.
 */
public interface CustomerService {
	
	/**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to find
     * @return an customer if found, otherwise empty {@link Customer}
     * @throws CustomException 
     */
	Customer findCustomerById(Long id) throws CustomException;

	Page<Customer> findAllCustomers(Pageable pagingInfo);

	Page<Customer> searchCustomer(String name, String address, String mobileNumber, Pageable pagingInfo) throws CustomException;

    Customer createCustomer(CustomerRequest customerRequest) throws CustomException;

    Customer updateCustomer(CustomerUpdateRequest customerRequest) throws CustomException;

    void deleteById(Long id) throws CustomException;
}

