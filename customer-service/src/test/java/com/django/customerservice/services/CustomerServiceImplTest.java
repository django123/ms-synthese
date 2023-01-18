package com.django.customerservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.django.customerservice.dto.CustomerDto;
import com.django.customerservice.entities.Customer;
import com.django.customerservice.mappers.CustomerMapper;
import com.django.customerservice.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    /**
     * Method under test: {@link CustomerServiceImpl#save(CustomerDto)}
     */
    @Test
    void testSave() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId("42");
        customer.setName("Name");
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId("42");
        customer1.setName("Name");
        when(customerMapper.mapDtoToCustomer((Customer) any())).thenReturn(customerDto);
        when(customerMapper.customerToCustomerDto((CustomerDto) any())).thenReturn(customer1);

        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setEmail("jane.doe@example.org");
        customerDto1.setId("42");
        customerDto1.setName("Name");
        assertSame(customerDto, customerServiceImpl.save(customerDto1));
        verify(customerRepository).save((Customer) any());
        verify(customerMapper).mapDtoToCustomer((Customer) any());
        verify(customerMapper).customerToCustomerDto((CustomerDto) any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#update(CustomerDto)}
     */
    @Test
    void testUpdate() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId("42");
        customer.setName("Name");
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId("42");
        customer1.setName("Name");
        when(customerMapper.mapDtoToCustomer((Customer) any())).thenReturn(customerDto);
        when(customerMapper.customerToCustomerDto((CustomerDto) any())).thenReturn(customer1);

        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setEmail("jane.doe@example.org");
        customerDto1.setId("42");
        customerDto1.setName("Name");
        assertSame(customerDto, customerServiceImpl.update(customerDto1));
        verify(customerRepository).save((Customer) any());
        verify(customerMapper).mapDtoToCustomer((Customer) any());
        verify(customerMapper).customerToCustomerDto((CustomerDto) any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#delete(String)}
     */
    @Test
    void testDelete() {
        doNothing().when(customerRepository).deleteById((String) any());
        customerServiceImpl.delete("42");
        verify(customerRepository).deleteById((String) any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#findAll()}
     */
    @Test
    void testFindAll() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(customerServiceImpl.findAll().isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerServiceImpl#findAll()}
     */
    @Test
    void testFindAll2() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId("42");
        customer.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findAll()).thenReturn(customerList);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        when(customerMapper.mapDtoToCustomer((Customer) any())).thenReturn(customerDto);
        assertEquals(1, customerServiceImpl.findAll().size());
        verify(customerRepository).findAll();
        verify(customerMapper).mapDtoToCustomer((Customer) any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#findAll()}
     */
    @Test
    void testFindAll3() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId("42");
        customer.setName("Name");

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId("42");
        customer1.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer);
        when(customerRepository.findAll()).thenReturn(customerList);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        when(customerMapper.mapDtoToCustomer((Customer) any())).thenReturn(customerDto);
        assertEquals(2, customerServiceImpl.findAll().size());
        verify(customerRepository).findAll();
        verify(customerMapper, atLeast(1)).mapDtoToCustomer((Customer) any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#findById(String)}
     */
    @Test
    void testFindById() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId("42");
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((String) any())).thenReturn(ofResult);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        when(customerMapper.mapDtoToCustomer((Customer) any())).thenReturn(customerDto);
        assertSame(customerDto, customerServiceImpl.findById("42"));
        verify(customerRepository).findById((String) any());
        verify(customerMapper).mapDtoToCustomer((Customer) any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#findById(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindById2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at com.django.customerservice.services.CustomerServiceImpl.findById(CustomerServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        when(customerRepository.findById((String) any())).thenReturn(Optional.empty());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        when(customerMapper.mapDtoToCustomer((Customer) any())).thenReturn(customerDto);
        customerServiceImpl.findById("42");
    }
}

