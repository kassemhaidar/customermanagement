package com.codechallenge.customermanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.request.CustomerRequest;
import com.codechallenge.customermanagement.business.domain.request.CustomerUpdateRequest;
import com.codechallenge.customermanagement.business.service.CustomerManagementService;
import com.codechallenge.customermanagement.business.service.MessageService;
import com.codechallenge.customermanagement.business.service.impl.CustomerServiceImpl;
import com.codechallenge.customermanagement.component.RequestReaderComponent;
import com.codechallenge.customermanagement.constant.Constants;
import com.codechallenge.customermanagement.data.entity.Customer;
import com.codechallenge.customermanagement.data.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerManagementService customerManagementService;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Customer customer;
    private CustomerRequest customerRequest;
    private CustomerUpdateRequest customerUpdateRequest;
	
    @Mock
	MessageService messageService;
    @Mock
	RequestReaderComponent requestReaderComponent;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("Customer Plus");
        customer.setAddress("Main St 1");
        customer.setMobileNumber("009613123123");

        customerRequest = new CustomerRequest();
        customerRequest.setName("Customer Plus");
        customerRequest.setAddress("Main St 1");
        customerRequest.setMobileNumber("+96170525354");

        customerUpdateRequest = new CustomerUpdateRequest();
        customerUpdateRequest.setId(1L);
        customerUpdateRequest.setName("Customer Plus");
        customerUpdateRequest.setAddress("Main Street");
        customerUpdateRequest.setMobileNumber("+96170525354");
    }

    @Test
    void testCreateCustomer() throws CustomException {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer customer = customerServiceImpl.createCustomer(customerRequest);

        assertNotNull(customer);
        assertEquals("Customer Plus", customer.getName());
    }

    @Test
    void testUpdateCustomer() throws CustomException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer customer = customerServiceImpl.updateCustomer(customerUpdateRequest);

        assertNotNull(customer);
        assertEquals("Customer Plus", customer.getName());
        assertEquals("Main Street", customer.getAddress());
        assertEquals("+96170525354", customer.getMobileNumber());
    }

    @Test
    void testFindCustomerById() throws CustomException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer customer = customerServiceImpl.findCustomerById(1L);

        assertNotNull(customer);
        assertEquals("Customer Plus", customer.getName());
    }

    @Test
    void testFindAllCustomers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(Arrays.asList(customer));
        when(customerRepository.findAll(pageable)).thenReturn(page);

        Page<Customer> customerPage = customerServiceImpl.findAllCustomers(pageable);

        assertNotNull(customerPage);
        assertEquals(1, customerPage.getSize());
        assertEquals("Customer Plus", customerPage.getContent().get(0).getName());
    }

    @Test
    void testSearchCustomer() throws CustomException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(Arrays.asList(customer));
        when(customerRepository.searchCustomer(eq("Customer Plus"), isNull(), isNull(), eq(pageable))).thenReturn(page);

        Page<Customer> customerPage = customerServiceImpl.searchCustomer("Customer Plus", null, null, pageable);

        assertNotNull(customerPage);
        assertEquals(1, customerPage.getSize());
        assertEquals("Customer Plus", customerPage.getContent().get(0).getName());
    }

    @Test
    void testSearchCustomerNull() throws CustomException {

        Pageable pageable = PageRequest.of(0, 10);
        String expectedMessage = "At least one field must be filled"; // Adjust this to match your expected message

        when(requestReaderComponent.getLanguage()).thenReturn("en"); // Mock the language return value
        when(messageService.getMessage("at.least.one.value.required", null, "en")).thenReturn(expectedMessage);

        CustomException exception = assertThrows(CustomException.class, () -> {
            customerServiceImpl.searchCustomer(null, null, null, pageable);
        });

        assertEquals(Constants.AT_LEAST_ONE_VALUE_IS_REQUIRED, exception.getMessage());
    }
}

