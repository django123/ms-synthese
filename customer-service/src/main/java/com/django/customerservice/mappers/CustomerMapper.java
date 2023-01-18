package com.django.customerservice.mappers;

import com.django.customerservice.dto.CustomerDto;
import com.django.customerservice.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerToCustomerDto(CustomerDto customerDto);
    CustomerDto mapDtoToCustomer(Customer customer);
}
