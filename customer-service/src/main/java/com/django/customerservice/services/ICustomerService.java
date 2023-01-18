package com.django.customerservice.services;

import com.django.customerservice.dto.CustomerDto;
import com.django.customerservice.entities.Customer;

import java.util.List;

public interface ICustomerService {

    CustomerDto save(CustomerDto customerDto);
    CustomerDto update(CustomerDto customerDto);
    void delete(String id);
    List<CustomerDto> findAll();
    CustomerDto findById(String id);
}
