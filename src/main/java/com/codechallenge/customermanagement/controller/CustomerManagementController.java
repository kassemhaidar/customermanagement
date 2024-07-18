package com.codechallenge.customermanagement.controller;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.request.CustomerRequest;
import com.codechallenge.customermanagement.business.domain.request.CustomerUpdateRequest;
import com.codechallenge.customermanagement.business.domain.response.CustomerResponse;
import com.codechallenge.customermanagement.business.service.CustomerService;
import com.codechallenge.customermanagement.business.service.MessageService;
import com.codechallenge.customermanagement.component.RequestReaderComponent;
import com.codechallenge.customermanagement.constant.Constants;
import com.codechallenge.customermanagement.data.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping(path = "/api/custmanag")
@Tag(name = Constants.CUSTOMER_MANAGEMENT, description = Constants.API_MANAGING_CUSTOMERS)
public class CustomerManagementController {

	@Autowired
    private CustomerService customerService;
	
	private static ModelMapper modelMapper = new ModelMapper();
    
    private static final Logger log = LoggerFactory.getLogger(CustomerManagementController.class);
	
    @Autowired
	MessageService messageService;
	@Autowired
	RequestReaderComponent requestReaderComponent;

	/**
     * Finds a customer by ID. (GET /api/custmanag/{id})
     *
     * @param id the ID of the customer to find
     * @return the found customer {@link ResponseEntity<CustomerResponse>}
	 * @throws CustomException 
     */
	@Operation(summary = Constants.FIND_CUSTOMER_BY_ID_SUMMARY, 
			description = Constants.FIND_CUSTOMER_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = Constants.CUSTOMER_FOUND,
            content = { @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = CustomerResponse.class)) }),
        @ApiResponse(responseCode = "400", description = Constants.INVALID_CUSTOMER_ID,
            content = @Content),
        @ApiResponse(responseCode = "401", description = Constants.UNAUTHORIZED,
    		content = @Content),
        @ApiResponse(responseCode = "404", description = Constants.CUSTOMER_FOUND,
            content = @Content)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable Long id) throws CustomException {
		log.info("in findCustomerById() >>> id: " + id);
    	Customer customer = customerService.findCustomerById(id);
		CustomerResponse customerResponse = convertCustomerToCustomerResponse(customer);
		return ResponseEntity.ok(customerResponse);
    }

	/**
     * Retrieves all customers with pagination support. (GET /api/custmanag)
     *
     * @param page the page number
     * @param pageSize the page size
     * @return a list of customers {@link ResponseEntity<List<CustomerResponse>>}
     */
	@Operation(summary = Constants.GET_ALL_CUSTOMERS_SUMMARY, 
			description = Constants.GET_ALL_CUSTOMERS_DESCRIPTION)
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(
    		@RequestParam(value = Constants.PAGINATION_PAGE_PARAMETER, required = false, defaultValue = Constants.DEFAULT_PAGE) int page,
			@RequestParam(value = Constants.PAGINATION_PAGE_SIZE_PARAMETER, required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize) {
		Pageable pagingInfo = PageRequest.of(page, pageSize);
		
		Page<Customer> customerSaved = customerService.findAllCustomers(pagingInfo);
		List<CustomerResponse> customerResponseList = new ArrayList<CustomerResponse>();
		for(Customer customer : customerSaved.getContent()) {
			customerResponseList.add(convertCustomerToCustomerResponse(customer));
		}
		
        return ResponseEntity.ok(customerResponseList);
    }

	 /**
     * Searches for customers by name, address, or mobile number with pagination support. (GET /api/custmanag/search)
     *
     * @param name the name of the customer
     * @param address the address of the customer
     * @param mobileNumber the mobile number of the customer
     * @param page the page number
     * @param pageSize the page size
     * @return a list of customers matching the search criteria {@link ResponseEntity<List<CustomerResponse>>}
     * @throws CustomException if no search criteria are provided
     */
	@Operation(summary = Constants.SEARCH_CUSTOMERS_SUMMARY, 
			description = Constants.SEARCH_CUSTOMERS_SUMMARY)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = Constants.CUSTOMERS_FOUND,
            content = { @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = CustomerResponse.class)) }),
        @ApiResponse(responseCode = "400", description = Constants.INVALID_SEARCH_PARAMETRS,
            content = @Content),
        @ApiResponse(responseCode = "401", description = Constants.UNAUTHORIZED,
    		content = @Content),
        @ApiResponse(responseCode = "404", description = Constants.CUSTOMERS_NOT_FOUND,
            content = @Content)
    })
    @GetMapping(value = "/search")
    public ResponseEntity<List<CustomerResponse>> searchCustomer(String name, String address, String mobileNumber,
    		@RequestParam(value = Constants.PAGINATION_PAGE_PARAMETER, required = false, defaultValue = Constants.DEFAULT_PAGE) int page,
			@RequestParam(value = Constants.PAGINATION_PAGE_SIZE_PARAMETER, required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize)
			throws CustomException {
		Pageable pagingInfo = PageRequest.of(page, pageSize);
		Page<Customer> customerSaved = customerService.searchCustomer(name, address, mobileNumber, pagingInfo);
		List<CustomerResponse> customerResponseList = new ArrayList<CustomerResponse>();
		for(Customer customer : customerSaved.getContent()) {
			customerResponseList.add(convertCustomerToCustomerResponse(customer));
		}
        return ResponseEntity.ok(customerResponseList);
    }

	/**
     * Creates a new customer. (POST /api/custmanag)
     *
     * @param customerRequest the request containing the customer details
     * @return the created customer {@link ResponseEntity<CustomerResponse>}
	 * @throws CustomException 
     */
	// TODO create bulk api which takes list of customers and created them.
	@Operation(summary = Constants.CREATE_CUSTOMER_SUMMARY, 
			description = Constants.CREATE_CUSTOMER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = Constants.CUSTOMER_CREATED,
            content = { @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = CustomerResponse.class)) }),
        @ApiResponse(responseCode = "400", description = Constants.INVALID_MOBILE_NUMBER,
            content = @Content),
        @ApiResponse(responseCode = "401", description = Constants.UNAUTHORIZED,
    		content = @Content),
        @ApiResponse(responseCode = "500", description = Constants.CANT_CREATE_CUSTOMER,
    		content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) throws CustomException {
    	CustomerResponse customerResponse = convertCustomerToCustomerResponse(customerService.createCustomer(customerRequest));
        return ResponseEntity.ok(customerResponse);
    }

	/**
     * Updates an existing customer. (PUT /api/custmanag)
     *
     * @param customerUpdateRequest the request containing the updated customer details
     * @return the updated customer {@link ResponseEntity<CustomerResponse>}
	 * @throws CustomException 
     */
	/*
	 *  TODO update bulk api which takes list of customers and create customers with null or empty id 
	 *  and updates customer with given id if found.
	 */
	@Operation(summary = Constants.UPDATE_CUSTOMER_SUMMARY, 
			description = Constants.UPDATE_CUSTOMER_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = Constants.CUSTOMER_UPDATED,
            content = { @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = CustomerResponse.class)) }),
        @ApiResponse(responseCode = "400", description = Constants.INVALID_MOBILE_NUMBER,
            content = @Content),
        @ApiResponse(responseCode = "401", description = Constants.UNAUTHORIZED,
        	content = @Content),
        @ApiResponse(responseCode = "404", description = Constants.CUSTOMER_NOT_FOUND,
            content = @Content),
        @ApiResponse(responseCode = "500", description = Constants.ERROR_UPDATING_CUSTOMER,
        	content = @Content)
    })
    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerUpdateRequest customerUpdateRequest) throws CustomException {
		CustomerResponse customerResponse = convertCustomerToCustomerResponse(customerService.updateCustomer(customerUpdateRequest));
        return ResponseEntity.ok(customerResponse);
    }
	
	/**
     * Delete a customer by ID. (DELETE /api/custmanag/{id})
     *
     * @param id the ID of the customer to find
     * @return the found customer {@link ResponseEntity<?>}
	 * @throws CustomException 
     */
	// TODO delete bulk api which takes list of customers and delete them all if found.
	@Operation(summary = Constants.FIND_CUSTOMER_BY_ID_SUMMARY, 
			description = Constants.FIND_CUSTOMER_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = Constants.CUSTOMER_FOUND,
            content = { @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = CustomerResponse.class)) }),
        @ApiResponse(responseCode = "400", description = Constants.INVALID_CUSTOMER_ID,
            content = @Content),
        @ApiResponse(responseCode = "401", description = Constants.UNAUTHORIZED,
    		content = @Content),
        @ApiResponse(responseCode = "404", description = Constants.CUSTOMER_FOUND,
            content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws CustomException {
		customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * Converts a Customer entity to a CustomerResponse.
     *
     * @param customer the customer entity to convert
     * @return the converted customer response {@link CustomerResponse}
     */
    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {
		log.info(">>> customer: " + customer.toString());
    	return modelMapper.map(customer, CustomerResponse.class);
    }
}
