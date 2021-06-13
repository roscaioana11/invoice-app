package ro.fasttrackit.homework13.invoice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.homework13.invoice.dto.InvoiceDto;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class InvoiceApiClient {
    private final String baseUrl;
    private final RestTemplate rest;

    public InvoiceApiClient(@Value("${invoice.service.location:NOT_DEFINED}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.rest = new RestTemplate();
    }

    public List<InvoiceDto> getAllInvoices() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/invoices")
                .toUriString();
        log.info("Getting all invoices: " + url);
        return rest.exchange(url, GET, new HttpEntity<>(null), new ParameterizedTypeReference<List<InvoiceDto>>() {
        }).getBody();
    }

    public Optional<InvoiceDto> getInvoiceById(String invoiceId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/invoices/")
                .path(invoiceId)
                .toUriString();
        try {
            return ofNullable(rest.getForObject(url, InvoiceDto.class));
        } catch (HttpClientErrorException ex) {
            return empty();
        }
    }

    public InvoiceDto addInvoice(InvoiceDto invoice) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/invoices")
                .toUriString();

        return rest.postForObject(url, invoice, InvoiceDto.class);
    }
}
