package com.django.inventoryservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.django.inventoryservice.dto.InvoiceDto;
import com.django.inventoryservice.entities.Customer;
import com.django.inventoryservice.entities.Invoice;
import com.django.inventoryservice.feign.CustomerServiceRestClient;
import com.django.inventoryservice.mappers.InvoiceMapper;
import com.django.inventoryservice.repositories.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvoiceServiceImpl.class})
@ExtendWith(SpringExtension.class)
class InvoiceServiceImplTest {
    @MockBean
    private CustomerServiceRestClient customerServiceRestClient;

    @MockBean
    private InvoiceMapper invoiceMapper;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoice(String)}
     */
    @Test
    void testGetInvoice() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");
        Optional<Invoice> ofResult = Optional.of(invoice);
        when(invoiceRepository.findById((String) any())).thenReturn(ofResult);
        InvoiceDto invoiceDto = new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID");

        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any())).thenReturn(invoiceDto);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer1);
        InvoiceDto actualInvoice = invoiceServiceImpl.getInvoice("42");
        assertSame(invoiceDto, actualInvoice);
        assertEquals("42", actualInvoice.getAmount().toString());
        verify(invoiceRepository).findById((String) any());
        verify(invoiceMapper).invoiceToInvoiceDTO((Invoice) any());
        verify(customerServiceRestClient).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoice(String)}
     */
    @Test
    void testGetInvoice2() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");
        Optional<Invoice> ofResult = Optional.of(invoice);
        when(invoiceRepository.findById((String) any())).thenReturn(ofResult);
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));
        when(customerServiceRestClient.customerById((String) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> invoiceServiceImpl.getInvoice("42"));
        verify(invoiceRepository).findById((String) any());
        verify(customerServiceRestClient).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoice(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetInvoice3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.django.inventoryservice.repositories.InvoiceRepository.findById(Object)" is null
        //       at com.django.inventoryservice.services.InvoiceServiceImpl.getInvoice(InvoiceServiceImpl.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        when(invoiceRepository.findById((String) any())).thenReturn(null);
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer);
        invoiceServiceImpl.getInvoice("42");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoice(String)}
     */
    @Test
    void testGetInvoice4() {
        when(invoiceRepository.findById((String) any())).thenReturn(Optional.empty());
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer);
        assertThrows(RuntimeException.class, () -> invoiceServiceImpl.getInvoice("42"));
        verify(invoiceRepository).findById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#listInvoices()}
     */
    @Test
    void testListInvoices() {
        when(invoiceRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(invoiceServiceImpl.listInvoices().isEmpty());
        verify(invoiceRepository).findAll();
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#listInvoices()}
     */
    @Test
    void testListInvoices2() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");

        ArrayList<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoiceList);
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer1);
        List<InvoiceDto> actualListInvoicesResult = invoiceServiceImpl.listInvoices();
        assertEquals(1, actualListInvoicesResult.size());
        assertEquals("42", actualListInvoicesResult.get(0).getAmount().toString());
        verify(invoiceRepository).findAll();
        verify(invoiceMapper).invoiceToInvoiceDTO((Invoice) any());
        verify(customerServiceRestClient).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#listInvoices()}
     */
    @Test
    void testListInvoices3() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");

        ArrayList<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoiceList);
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));
        when(customerServiceRestClient.customerById((String) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> invoiceServiceImpl.listInvoices());
        verify(invoiceRepository).findAll();
        verify(customerServiceRestClient).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#listInvoices()}
     */
    @Test
    void testListInvoices4() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");

        Invoice invoice1 = new Invoice();
        invoice1.setAmount(BigDecimal.valueOf(42L));
        invoice1.setCustomer(customer1);
        invoice1.setCustomerID("Customer ID");
        invoice1.setDate(LocalDate.ofEpochDay(1L));
        invoice1.setId("42");

        ArrayList<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice1);
        invoiceList.add(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoiceList);
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(123L);
        customer2.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer2);
        List<InvoiceDto> actualListInvoicesResult = invoiceServiceImpl.listInvoices();
        assertEquals(2, actualListInvoicesResult.size());
        assertEquals("42", actualListInvoicesResult.get(1).getAmount().toString());
        assertEquals("42", actualListInvoicesResult.get(0).getAmount().toString());
        verify(invoiceRepository).findAll();
        verify(invoiceMapper, atLeast(1)).invoiceToInvoiceDTO((Invoice) any());
        verify(customerServiceRestClient, atLeast(1)).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#newInvoice(InvoiceDto)}
     */
    @Test
    void testNewInvoice() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");
        when(invoiceRepository.save((Invoice) any())).thenReturn(invoice);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");

        Invoice invoice1 = new Invoice();
        invoice1.setAmount(BigDecimal.valueOf(42L));
        invoice1.setCustomer(customer1);
        invoice1.setCustomerID("Customer ID");
        invoice1.setDate(LocalDate.ofEpochDay(1L));
        invoice1.setId("42");
        InvoiceDto invoiceDto = new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID");

        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any())).thenReturn(invoiceDto);
        when(invoiceMapper.invoiceDTOtoInvoice((InvoiceDto) any())).thenReturn(invoice1);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(123L);
        customer2.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer2);
        InvoiceDto actualNewInvoiceResult = invoiceServiceImpl
                .newInvoice(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));
        assertSame(invoiceDto, actualNewInvoiceResult);
        assertEquals("42", actualNewInvoiceResult.getAmount().toString());
        verify(invoiceRepository).save((Invoice) any());
        verify(invoiceMapper).invoiceToInvoiceDTO((Invoice) any());
        verify(invoiceMapper).invoiceDTOtoInvoice((InvoiceDto) any());
        verify(customerServiceRestClient, atLeast(1)).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#newInvoice(InvoiceDto)}
     */
    @Test
    void testNewInvoice2() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");
        when(invoiceRepository.save((Invoice) any())).thenReturn(invoice);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");

        Invoice invoice1 = new Invoice();
        invoice1.setAmount(BigDecimal.valueOf(42L));
        invoice1.setCustomer(customer1);
        invoice1.setCustomerID("Customer ID");
        invoice1.setDate(LocalDate.ofEpochDay(1L));
        invoice1.setId("42");
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));
        when(invoiceMapper.invoiceDTOtoInvoice((InvoiceDto) any())).thenReturn(invoice1);
        when(customerServiceRestClient.customerById((String) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,
                () -> invoiceServiceImpl.newInvoice(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID")));
        verify(customerServiceRestClient).customerById((String) any());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#newInvoice(InvoiceDto)}
     */
    @Test
    void testNewInvoice3() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");
        when(invoiceRepository.save((Invoice) any())).thenReturn(invoice);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");

        Invoice invoice1 = new Invoice();
        invoice1.setAmount(BigDecimal.valueOf(42L));
        invoice1.setCustomer(customer1);
        invoice1.setCustomerID("Customer ID");
        invoice1.setDate(LocalDate.ofEpochDay(1L));
        invoice1.setId("42");
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));
        when(invoiceMapper.invoiceDTOtoInvoice((InvoiceDto) any())).thenReturn(invoice1);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(123L);
        customer2.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer2);
        assertThrows(RuntimeException.class, () -> invoiceServiceImpl.newInvoice(null));
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#newInvoice(InvoiceDto)}
     */
    @Test
    void testNewInvoice4() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(123L);
        customer.setName("Name");

        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.valueOf(42L));
        invoice.setCustomer(customer);
        invoice.setCustomerID("Customer ID");
        invoice.setDate(LocalDate.ofEpochDay(1L));
        invoice.setId("42");
        when(invoiceRepository.save((Invoice) any())).thenReturn(invoice);

        Customer customer1 = new Customer();
        customer1.setEmail("jane.doe@example.org");
        customer1.setId(123L);
        customer1.setName("Name");

        Invoice invoice1 = new Invoice();
        invoice1.setAmount(BigDecimal.valueOf(42L));
        invoice1.setCustomer(customer1);
        invoice1.setCustomerID("Customer ID");
        invoice1.setDate(LocalDate.ofEpochDay(1L));
        invoice1.setId("42");
        when(invoiceMapper.invoiceToInvoiceDTO((Invoice) any()))
                .thenReturn(new InvoiceDto(BigDecimal.valueOf(42L), "Customer ID"));
        when(invoiceMapper.invoiceDTOtoInvoice((InvoiceDto) any())).thenReturn(invoice1);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(123L);
        customer2.setName("Name");
        when(customerServiceRestClient.customerById((String) any())).thenReturn(customer2);
        InvoiceDto invoiceDto = mock(InvoiceDto.class);
        when(invoiceDto.getCustomerID()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> invoiceServiceImpl.newInvoice(invoiceDto));
        verify(invoiceDto).getCustomerID();
    }
}

