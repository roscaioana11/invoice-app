package ro.fasttrackit.homework13.invoice.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import ro.fasttrackit.homework13.invoice.dto.InvoiceDto;

@Value
@Builder
@JsonDeserialize(builder = InvoiceEvent.InvoiceEventBuilder.class)
public class InvoiceEvent {
    InvoiceDto invoice;
    InvoiceEventType type;

    @JsonPOJOBuilder(withPrefix = "")
    public static class InvoiceEventBuilder{

    }
}
