package com.django.customerservice.services;

import com.django.customerservice.dto.CustomerDto;
import com.django.customerservice.entities.Customer;
import com.django.customerservice.mappers.CustomerMapper;
import com.django.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = customerMapper.customerToCustomerDto(customerDto);
        Customer saveCustomer = customerRepository.save(customer);
        return customerMapper.mapDtoToCustomer(saveCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer customer = customerMapper.customerToCustomerDto(customerDto);
        Customer saveCustomer = customerRepository.save(customer);
        return customerMapper.mapDtoToCustomer(saveCustomer);
    }

    @Override
    public void delete(String id) {
       customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                        .map(customer -> customerMapper.mapDtoToCustomer(customer))
                        .collect(Collectors.toList());
    }

    @Override
    public CustomerDto findById(String id) {
        return customerMapper.mapDtoToCustomer(customerRepository.findById(id).get());
    }
}
