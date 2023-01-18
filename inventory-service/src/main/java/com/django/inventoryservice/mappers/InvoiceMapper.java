package com.django.inventoryservice.mappers;

import com.django.inventoryservice.dto.InvoiceDto;
import com.django.inventoryservice.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper{

    InvoiceDto invoiceToInvoiceDTO(Invoice invoice);
    Invoice invoiceDTOtoInvoice(InvoiceDto invoiceRequestDTO);
}
