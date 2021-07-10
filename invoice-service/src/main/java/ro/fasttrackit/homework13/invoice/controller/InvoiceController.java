package ro.fasttrackit.homework13.invoice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework13.exceptions.ResourceNotFoundException;
import ro.fasttrackit.homework13.invoice.dto.InvoiceDto;
import ro.fasttrackit.homework13.invoice.model.mappers.InvoiceMappers;
import ro.fasttrackit.homework13.invoice.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvoiceController {
    private final InvoiceService service;
    private final InvoiceMappers mapper;

    @GetMapping
    List<InvoiceDto> getAllInvoices() {
        return mapper.toApi(service.getAllInvoices());
    }

    @GetMapping("{invoiceId}")
    InvoiceDto getInvoiceById(@PathVariable String invoiceId){
        return service.getInvoiceById(invoiceId)
                .map(mapper::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find invoice with id " + invoiceId));
    }

    @PostMapping
    InvoiceDto addInvoice(@RequestBody InvoiceDto invoice){
        return mapper.toApi(service.addInvoice(mapper.toDb(invoice)));
    }

    @PutMapping("/{invoiceId}")
    InvoiceDto putInvoice(@RequestBody InvoiceDto newInvoice,
                                 @PathVariable String invoiceId){
        return mapper.toApi(service.putInvoice(mapper.toDb(newInvoice), invoiceId));
    }

    @DeleteMapping("{invoiceId}")
    void deleteInvoice(@PathVariable String invoiceId){
        service.deleteInvoice(invoiceId);
    }
}
