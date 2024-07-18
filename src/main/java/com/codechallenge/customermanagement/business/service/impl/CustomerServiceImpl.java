package com.codechallenge.customermanagement.business.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.request.CustomerRequest;
import com.codechallenge.customermanagement.business.domain.request.CustomerUpdateRequest;
import com.codechallenge.customermanagement.business.domain.response.CustomerResponse;
import com.codechallenge.customermanagement.business.service.CustomerManagementService;
import com.codechallenge.customermanagement.business.service.CustomerService;
import com.codechallenge.customermanagement.business.service.MessageService;
import com.codechallenge.customermanagement.component.RequestReaderComponent;
import com.codechallenge.customermanagement.constant.Constants;
import com.codechallenge.customermanagement.data.entity.Customer;
import com.codechallenge.customermanagement.data.repository.CustomerRepository;

/**
 * Service class for managing customers.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerManagementService customerManagementService;

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
    @Autowired
	MessageService messageService;
	@Autowired
	RequestReaderComponent requestReaderComponent;

	private static ModelMapper modelMapper = new ModelMapper();
	
	/**
     * {@inheritDoc}
     */
    @Override
	public Customer findCustomerById(Long id) throws CustomException {
		log.info("in findCustomer() >>> id: " + id);
		Optional<Customer> customer = customerRepository.findById(id);
    	if(customer.isPresent()) {
        	// TODO Customer.toString() to be readable response
    		log.info("in findCustomer() >>> customer found " + customer.toString());
    		return customer.get();
    	}
		log.error("in findCustomer() >>> customer not found >>> " + Constants.INVALID_CUSTOMER_ID + ": " + id);
    	throw new CustomException(
    			messageService.getMessage("invalid.customer.id",null,requestReaderComponent.getLanguage()) + ": " + id);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Customer> findAllCustomers(Pageable pagingInfo) {
		log.info("in findAllCustomers() >>> ");
    	Pageable pageable = PageRequest.of(pagingInfo.getPageNumber(), pagingInfo.getPageSize());
    	Page<Customer> customerSaved = customerRepository.findAll(pageable);
    	if(customerSaved.hasContent() && !CollectionUtils.isEmpty(customerSaved.getContent())) {
    		log.info("in findAllCustomers() >>> customers found ");
    	} else {
    		log.warn("in findAllCustomers() >>> no customers found ");
    	}
    	return customerSaved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Customer> searchCustomer(String name, String address, String mobileNumber, Pageable pagingInfo)
    	throws CustomException {
		log.info("in searchCustomer() >>> name:" + name + ", address:" + address + ", mobileNumber:" + mobileNumber);
    	if(StringUtils.isEmpty(name) && StringUtils.isEmpty(address) && StringUtils.isEmpty(mobileNumber)) {
    		log.error("in searchCustomer() >>> " + Constants.AT_LEAST_ONE_VALUE_IS_REQUIRED);
    		throw new CustomException(
    				messageService.getMessage("at.least.one.value.required",null,requestReaderComponent.getLanguage()));
    	}
    	Pageable pageable = PageRequest.of(pagingInfo.getPageNumber(), pagingInfo.getPageSize());
    	Page<Customer> customerList = customerRepository.searchCustomer(name, address, mobileNumber, pageable);
    	if(customerList.hasContent() && !CollectionUtils.isEmpty(customerList.getContent())) {
    		log.info("in searchCustomer() >>> customers found ");
    	}
    	return customerList;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public Customer createCustomer(CustomerRequest customerRequest) throws CustomException {
		log.info("in createCustomer() >>> ");
        // Validate mobile number using the mobile validation microservice
    	try {
			customerManagementService.validateMobileNumber(customerRequest.getMobileNumber());
		} catch (CustomException ex) {
    		log.error("in createCustomer() >>> " + ex.getMessage());
    		ex.printStackTrace();
			throw ex;
		}
    	Customer customer = convertCustomerRequestToCustomer(customerRequest);
    	Customer customerSaved = customerRepository.save(customer);
    	
    	return customerSaved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer updateCustomer(CustomerUpdateRequest customerRequest) throws CustomException {
		log.info("in updateCustomer() >>> ");
    	Customer customer;
    	try {
    		customer = findCustomerById(customerRequest.getId());
    	} catch (CustomException ex) {
    		log.error("in updateCustomer() >>> " + ex.getMessage());
    		ex.printStackTrace();
			throw ex;
		}
    	if(customer != null) {
        	if(!customer.getMobileNumber().equals(customerRequest.getMobileNumber())) {
    	        // Validate mobile number using the mobile validation microservice
    	    	try {
					customerManagementService.validateMobileNumber(customerRequest.getMobileNumber());
				} catch (CustomException ex) {
		    		log.error("in updateCustomer() >>> " + ex.getMessage());
		    		ex.printStackTrace();
					throw ex;
				}
    	    	customer.setMobileNumber(customerRequest.getMobileNumber());
        	}
        	customer.setAddress(customerRequest.getAddress());
        	customer.setName(customerRequest.getName());
        	Customer customerSaved = customerRepository.save(customer);
        	return customerSaved;
    	}
    	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) throws CustomException {
		log.info("in deleteById() >>> id: " + id);
    	Customer customer = findCustomerById(id);
    	if(customer != null) {
        	// TODO Customer.toString() to be readable response
    		log.info("in findCustomer() >>> customer found " + customer.toString());
    		customerRepository.delete(customer);
    	}
		log.error("in deleteById() >>> customer not found >>> " + Constants.INVALID_CUSTOMER_ID + ": " + id);
    	throw new CustomException(
    			messageService.getMessage("invalid.customer.id",null,requestReaderComponent.getLanguage()));
    }
    
    /**
     * Converts a Customer entity to a CustomerResponse.
     *
     * @param customer the customer entity to convert
     * @return the converted customer response {@link CustomerResponse}
     */
    private Customer convertCustomerRequestToCustomer(CustomerRequest customerRequest) {
		log.info(">>> customerRequest: " + customerRequest.toString());
    	return modelMapper.map(customerRequest, Customer.class);
    }

}

