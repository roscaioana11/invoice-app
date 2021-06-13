package ro.fasttrackit.homework13.invoice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13.exceptions.ValidationException;
import ro.fasttrackit.homework13.invoice.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class InvoiceValidator {
    private final InvoiceRepository repository;

    private Optional<ValidationException> exists(String invoiceId) {
        return repository.existsById(invoiceId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Invoice with id " + invoiceId + " doesn't exist")));
    }

    public void validateExistsOrThrow(String invoiceId) {
        exists(invoiceId).ifPresent(ex -> {
            throw ex;
        });
    }
}
