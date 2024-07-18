package com.codechallenge.customermanagement.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Customer.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    /**
     * The unique identifier of the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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