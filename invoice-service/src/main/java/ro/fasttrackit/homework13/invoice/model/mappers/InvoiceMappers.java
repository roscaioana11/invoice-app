package ro.fasttrackit.homework13.invoice.model.mappers;

import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13.invoice.dto.InvoiceDto;
import ro.fasttrackit.homework13.invoice.model.InvoiceEntity;
import ro.fasttrackit.homework13.utils.ModelMappers;

@Component
public class InvoiceMappers implements ModelMappers<InvoiceDto, InvoiceEntity> {
    @Override
    public InvoiceDto toApi(InvoiceEntity source) {
        return InvoiceDto.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .sender(source.getSender())
                .receiver(source.getReceiver())
                .payed(source.isPayed())
                .build();
    }

    @Override
    public InvoiceEntity toDb(InvoiceDto source) {
        return InvoiceEntity.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .sender(source.getSender())
                .receiver(source.getReceiver())
                .payed(source.isPayed())
                .build();
    }
}
