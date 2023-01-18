package com.django.customerservice;

import com.django.customerservice.entities.Customer;
import com.django.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository){
		return args -> {
			customerRepository.save(new Customer("001", "edougajean@gmail.com", "edouga"));
			customerRepository.save(new Customer("002", "jean@gmail.com", "enzo kenza"));
		};
	}
}
