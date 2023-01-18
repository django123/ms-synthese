package com.django.customerservice.api;

import com.django.customerservice.dto.CustomerDto;
import com.django.customerservice.services.ICustomerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerRestController {

    private final ICustomerService customerService;

    public CustomerRestController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getCustomers(){

        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable String id){
        return customerService.findById(id);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public CustomerDto save(@Valid @RequestBody CustomerDto customerRequestDto){
        return customerService.save(customerRequestDto);
    }

    @PutMapping("/update")
    public CustomerDto update(@Valid @RequestBody CustomerDto customerRequestDto){
        return customerService.update(customerRequestDto);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public void delete(@PathVariable String id){
        customerService.delete(id);
    }
}
