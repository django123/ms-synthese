package com.django.inventoryservice;

import com.django.inventoryservice.dto.InvoiceDto;
import com.django.inventoryservice.services.IInvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(IInvoiceService invoiceService){
		return args -> {
			invoiceService.newInvoice(new InvoiceDto(new BigDecimal(8700),"001"));
			invoiceService.newInvoice(new InvoiceDto(new BigDecimal(5400),"002"));
		} ;
	}
}
