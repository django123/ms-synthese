package com.django.inventoryservice.services;

import com.django.inventoryservice.dto.InvoiceDto;

import java.util.List;

public interface IInvoiceService {
    InvoiceDto getInvoice(String id);
    List<InvoiceDto> listInvoices();
    InvoiceDto newInvoice(InvoiceDto invoiceRequestDTO);
}
