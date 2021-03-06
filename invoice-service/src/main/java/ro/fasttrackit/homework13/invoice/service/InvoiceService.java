package ro.fasttrackit.homework13.invoice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework13.exceptions.ResourceNotFoundException;
import ro.fasttrackit.homework13.invoice.model.InvoiceEntity;
import ro.fasttrackit.homework13.invoice.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceNotifications invoiceNotifications;
    private final InvoiceValidator validator;

    public List<InvoiceEntity> getAllInvoices(){
        return invoiceRepository.findAll();
    }

    public Optional<InvoiceEntity> getInvoiceById(String invoiceId){
        validator.validateExistsOrThrow(invoiceId);
        return invoiceRepository.findById(invoiceId);
    }

    public InvoiceEntity addInvoice(InvoiceEntity newInvoice){
        log.info("Adding " + newInvoice);
        newInvoice.setPayed(false);
        InvoiceEntity savedEntity = invoiceRepository.save(newInvoice);
        invoiceNotifications.notifyInvoiceAdded(newInvoice);
        return savedEntity;
    }

    public InvoiceEntity updateInvoiceStatus(String invoiceId) {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find invoice with id " + invoiceId));
        invoiceEntity.setPayed(true);
        return invoiceRepository.save(invoiceEntity);
    }

    public InvoiceEntity putInvoice(InvoiceEntity newInvoice, String invoiceId){
        newInvoice.setId(invoiceId);

        InvoiceEntity dbInvoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find invoice with id " + invoiceId));
        copyInvoice(newInvoice, dbInvoice);
        return invoiceRepository.save(dbInvoice);
    }

    private void copyInvoice(InvoiceEntity newInvoice, InvoiceEntity dbInvoice) {
        dbInvoice.setSender(newInvoice.getSender());
        dbInvoice.setReceiver(newInvoice.getReceiver());
        dbInvoice.setAmount(newInvoice.getAmount());
        dbInvoice.setDescription(newInvoice.getDescription());
    }

    public void deleteInvoice(String invoiceId){
        invoiceRepository.deleteById(invoiceId);
    }

}
