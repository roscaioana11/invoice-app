package ro.fasttrackit.homework13.invoice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework13.invoice.model.InvoiceEntity;

@Repository
public interface InvoiceRepository extends MongoRepository<InvoiceEntity, String> {
}
