package com.codechallenge.customermanagement.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codechallenge.customermanagement.data.entity.Customer;

/**
 * Repository interface for managing Customer entities.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
    /**
     * Retrieves all customers with pagination.
     *
     * @param pageable pagination information
     * @return a page of customers
     */
    Page<Customer> findAll(Pageable pageable);
	
    // TODO tune query to be not case sensitive.
    /**
     * Searches for customers based on the provided criteria with pagination.
     *
     * @param name the name to search for (can be null)
     * @param address the address to search for (can be null)
     * @param mobileNumber the mobile number to search for (can be null)
     * @param pageable pagination information
     * @return a page of customers matching the search criteria
     */
    @Query("SELECT c FROM Customer c WHERE " +
           "(:name IS NULL OR c.name LIKE %:name%) AND " +
           "(:address IS NULL OR c.address LIKE %:address%) AND " +
           "(:mobileNumber IS NULL OR c.mobileNumber LIKE %:mobileNumber%)")
    Page<Customer> searchCustomer(String name, String address, String mobileNumber, Pageable pageable);
}