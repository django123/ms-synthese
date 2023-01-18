package com.django.inventoryservice.api;

import com.django.inventoryservice.dto.InvoiceDto;
import com.django.inventoryservice.services.IInvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/invoices")
public class InvoiceController {

    private IInvoiceService invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<InvoiceDto> invoices(){
        return invoiceService.listInvoices(); }
    @GetMapping(path = "/{id}")
    public InvoiceDto invoice(@PathVariable String id){
        return invoiceService.getInvoice(id); }
    @PostMapping(path="/invoices")
    public InvoiceDto save(@RequestBody InvoiceDto invoiceRequestDTO){
        return invoiceService.newInvoice(invoiceRequestDTO); }
}
