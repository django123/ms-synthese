package com.django.inventoryservice.services;

import com.django.inventoryservice.dto.InvoiceDto;
import com.django.inventoryservice.entities.Customer;
import com.django.inventoryservice.entities.Invoice;
import com.django.inventoryservice.feign.CustomerServiceRestClient;
import com.django.inventoryservice.mappers.InvoiceMapper;
import com.django.inventoryservice.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements IInvoiceService{

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    private final CustomerServiceRestClient customerServiceRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              InvoiceMapper invoiceMapper,
                              CustomerServiceRestClient customerServiceRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerServiceRestClient = customerServiceRestClient;
    }

    @Override
    public InvoiceDto getInvoice(String id) {
        Invoice invoice=invoiceRepository.findById(id).orElse(null);
        if(invoice==null) throw new RuntimeException("Invoice Not found");
        Customer customer=customerServiceRestClient.customerById(invoice.getCustomerID());
        invoice.setCustomer(customer);
        return invoiceMapper.invoiceToInvoiceDTO(invoice);
    }

    @Override
    public List<InvoiceDto> listInvoices() {
        List<Invoice> invoices=invoiceRepository.findAll();
        for(Invoice invoice:invoices){
            Customer customer=customerServiceRestClient.customerById(invoice.getCustomerID());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                .map((inv)->invoiceMapper.invoiceToInvoiceDTO(inv))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDto newInvoice(InvoiceDto invoiceRequestDTO) {
        Customer customer;
        try {
            customer=customerServiceRestClient.customerById(invoiceRequestDTO.getCustomerID());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        Invoice invoice=invoiceMapper.invoiceDTOtoInvoice(invoiceRequestDTO);
        invoice.setCustomer(customer);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(LocalDate.now());
        Invoice savedInvoice=invoiceRepository.save(invoice);
        savedInvoice.setCustomer(customerServiceRestClient.customerById(savedInvoice.getCustomerID()));
        return invoiceMapper.invoiceToInvoiceDTO(savedInvoice);
    }
}
