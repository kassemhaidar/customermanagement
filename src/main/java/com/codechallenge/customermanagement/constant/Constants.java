package com.codechallenge.customermanagement.constant;

public class Constants {

	// TODO should be moved to file Constants in a common library and not implemented in every project.
	/* Authentication */
	public static final String UNAUTHORIZED = "Unauthorized";
	public static final String API_NOT_FOUND = "API Not FOund";
	public static final String USER = "admin";
	public static final String PASSWORD = "password";
	public static final String ROLE = "ADMIN";
	public static final String RESOURCE_NOT_FOUND = "Resource not found error occurred";
	public static final String GENERIC_ERROR_OCCURED = "Generic error occurred";
	
	/* Header Attributes */
	public static final String MISSING_REQUIRED_HEADER_ERROR = "MISSING_REQUIRED_HEADER_ERROR";
	public static final String ACCEPT_LANGUAGE_HEADER_KEY = "Accept-Language";
	public static final String CONTENT_LANGUAGE_HEADER_KEY = "Content-Language";
	public static final String DEFAULT_LANGUAGE = "EN";
	
	/* Validation */
	public static final String AT_LEAST_ONE_VALUE_IS_REQUIRED = "At least one field must be filled";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String INVALID_MOBILE_NUMBER = "Invalid Mobile Number";
	public static final String EXPECTED_ERROR_OCCURED = "An unexpected error occurred";

	/* Paging */
	public static final String DEFAULT_PAGE = "0";
	public static final String DEFAULT_PAGE_SIZE = "10";
	public static final String PAGINATION_PAGE_PARAMETER = "page";
	public static final String PAGINATION_PAGE_SIZE_PARAMETER = "pageSize";

	/* Swagger */
	public static final String CUSTOMER_MANAGEMENT = "Customer Management";
	public static final String API_MANAGING_CUSTOMERS = "APIs for managing customers";
	public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CANT_CREATE_CUSTOMER = "Can't Create a Customer";
    public static final String ERROR_UPDATING_CUSTOMER = "Error updating Customer";
    public static final String FIND_CUSTOMER_BY_ID_SUMMARY = "Find customer by ID";
    public static final String FIND_CUSTOMER_BY_ID_DESCRIPTION = "Returns a single customer by their ID.";
    public static final String CUSTOMER_FOUND = "Customer found";
    public static final String INVALID_CUSTOMER_ID = "Invalid Customer ID";
    
    public static final String GET_ALL_CUSTOMERS_SUMMARY = "Get all customers with pagination";
    public static final String GET_ALL_CUSTOMERS_DESCRIPTION = "Returns a list of all customers with pagination support.";
    
    public static final String SEARCH_CUSTOMERS_SUMMARY = "Search customers";
    public static final String SEARCH_CUSTOMERS_DESCRIPTION = "Searches for customers by name, address, or mobile number with pagination support.";
    public static final String CUSTOMERS_FOUND = "Customers found";
    public static final String CUSTOMERS_NOT_FOUND = "Customers not found";
    public static final String INVALID_SEARCH_PARAMETRS = "Invalid search parameters";
    
    public static final String CREATE_CUSTOMER_SUMMARY = "Create a new customer";
    public static final String CREATE_CUSTOMER_DESCRIPTION = "Creates a new customer with the provided details.";
    public static final String CUSTOMER_CREATED = "Customer created";
    
    public static final String UPDATE_CUSTOMER_SUMMARY = "Update an existing customer";
    public static final String UPDATE_CUSTOMER_DESCRIPTION = "Updates an existing customer with the provided details.";
    public static final String CUSTOMER_UPDATED = "Customer updated";
    
    public static final String APPLICATION_JSON = "application/json";
}
