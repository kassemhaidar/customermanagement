package com.codechallenge.customermanagement;

import com.codechallenge.customermanagement.business.domain.exception.CustomException;
import com.codechallenge.customermanagement.business.domain.request.CustomerRequest;
import com.codechallenge.customermanagement.business.domain.request.CustomerUpdateRequest;
import com.codechallenge.customermanagement.business.service.impl.CustomerServiceImpl;
import com.codechallenge.customermanagement.constant.Constants;
import com.codechallenge.customermanagement.data.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CustomerManagementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerServiceImpl;

    private Customer mockCustomer;

    private String basicAuthHeader;

    @BeforeEach
    void setUp() {
    	// Initialize controller
        //MockitoAnnotations.openMocks(this);

        // Set up basic auth header
        String username = "admin";
        String password = "password";
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        basicAuthHeader = "Basic " + new String(encodedAuth);
        // Create a mock CustomerResponse
        mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("Customer Plus");
        mockCustomer.setAddress("Main St 1");
        mockCustomer.setMobileNumber("009613123123");
    }

    @Test
    void testCreateCustomer_Success() throws Exception {
        // Mock service response
        when(customerServiceImpl.createCustomer(any())).thenReturn(mockCustomer);

        // Create request body
        CustomerRequest request = new CustomerRequest("Customer Plus", "Main St 1", "009613123123");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/custmanag")
                .header("Authorization", basicAuthHeader)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Customer Plus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Main St 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobileNumber").value("009613123123"));
    }
    
    @Test
    void testUpdateCustomer_Success() throws Exception {
        // Mock service response
        when(customerServiceImpl.updateCustomer(any())).thenReturn(mockCustomer);

        // Create request body
        CustomerUpdateRequest request = new CustomerUpdateRequest("Customer Plus", "Main St 1", "009613123123");
        request.setId(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/custmanag")
                .header("Authorization", basicAuthHeader)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Customer Plus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Main St 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobileNumber").value("009613123123"));
    }

    @Test
    void testFindCustomerById_ValidId_Success() throws Exception {
        // Mock service response
        when(customerServiceImpl.findCustomerById(anyLong())).thenReturn(mockCustomer);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/custmanag/{id}", 1L)
                .header("Authorization", basicAuthHeader)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Customer Plus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Main St 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobileNumber").value("009613123123"));
    }

    @Test
    void testGetAllCustomers_Success() throws Exception {
        // Mock service response
    	Page<Customer> mockPage = new PageImpl<>(Collections.singletonList(mockCustomer), PageRequest.of(0, 10), 1);

        when(customerServiceImpl.findAllCustomers(any())).thenReturn(mockPage);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/custmanag")
                .header("Authorization", basicAuthHeader)
                .param(Constants.PAGINATION_PAGE_PARAMETER, "0")
                .param(Constants.PAGINATION_PAGE_SIZE_PARAMETER, "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Customer Plus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("Main St 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mobileNumber").value("009613123123"));
    }

    @Test
    void testSearchCustomer_Success() throws Exception {
        // Mock service response
    	Page<Customer> mockPage = new PageImpl<>(Collections.singletonList(mockCustomer), PageRequest.of(0, 10), 1);
    	
        when(customerServiceImpl.searchCustomer(anyString(), anyString(), anyString(), any())).thenReturn(mockPage);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/custmanag/search")
                .header("Authorization", basicAuthHeader)
                .param("name", "John")
                .param("address", "123")
                .param("mobileNumber", "+123")
                .param(Constants.PAGINATION_PAGE_PARAMETER, "0")
                .param(Constants.PAGINATION_PAGE_SIZE_PARAMETER, "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Customer Plus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("Main St 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mobileNumber").value("009613123123"));
    }
    
    @Test
    void testSearchCustomer_Fail() throws Exception {
        // Mock service response should not be necessary for failure scenario
    	when(customerServiceImpl.searchCustomer(isNull(), isNull(), isNull(), any(Pageable.class))).thenThrow(new CustomException(Constants.AT_LEAST_ONE_VALUE_IS_REQUIRED));
    	
        // Perform GET request without required parameters
        mockMvc.perform(MockMvcRequestBuilders.get("/api/custmanag/search")
                .header("Authorization", basicAuthHeader)
                .param(Constants.PAGINATION_PAGE_PARAMETER, "0")
                .param(Constants.PAGINATION_PAGE_SIZE_PARAMETER, "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
