package com.django.inventoryservice.dto;

import com.django.inventoryservice.entities.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDto {

    private String id;
    private LocalDate date;
    private BigDecimal amount;
    private Customer customer;

    private String customerID;

    public InvoiceDto(BigDecimal amount, String customerID) {
        this.amount = amount;
        this.customerID = customerID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
