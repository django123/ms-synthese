package com.django.customerservice.api;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.django.customerservice.dto.CustomerDto;
import com.django.customerservice.services.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerRestController.class})
@ExtendWith(SpringExtension.class)
class CustomerRestControllerTest {
    @Autowired
    private CustomerRestController customerRestController;

    @MockBean
    private ICustomerService iCustomerService;

    /**
     * Method under test: {@link CustomerRestController#update(CustomerDto)}
     */
    @Test
    void testUpdate() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        when(iCustomerService.update((CustomerDto) any())).thenReturn(customerDto);

        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setEmail("jane.doe@example.org");
        customerDto1.setId("42");
        customerDto1.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/customers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"name\":\"Name\",\"email\":\"jane.doe@example.org\"}"));
    }

    /**
     * Method under test: {@link CustomerRestController#delete(String)}
     */
    @Test
    void testDelete() throws Exception {
        doNothing().when(iCustomerService).delete((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/customers/{id}", "42");
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CustomerRestController#delete(String)}
     */
    @Test
    void testDelete2() throws Exception {
        doNothing().when(iCustomerService).delete((String) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/customers/{id}", "42");
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CustomerRestController#getCustomer(String)}
     */
    @Test
    void testGetCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        when(iCustomerService.findById((String) any())).thenReturn(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customers/{id}", "42");
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"name\":\"Name\",\"email\":\"jane.doe@example.org\"}"));
    }

    /**
     * Method under test: {@link CustomerRestController#getCustomers()}
     */
    @Test
    void testGetCustomers() throws Exception {
        when(iCustomerService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customers");
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerRestController#getCustomers()}
     */
    @Test
    void testGetCustomers2() throws Exception {
        when(iCustomerService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/customers");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerRestController#save(CustomerDto)}
     */
    @Test
    void testSave() throws Exception {
        when(iCustomerService.findAll()).thenReturn(new ArrayList<>());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setId("42");
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

