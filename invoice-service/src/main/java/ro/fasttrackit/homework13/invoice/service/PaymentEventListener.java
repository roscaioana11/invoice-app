package ro.fasttrackit.homework13.invoice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13.invoice.model.InvoiceEntity;
import ro.fasttrackit.homework13.payment.events.PaymentEvent;

import static ro.fasttrackit.homework13.payment.events.Status.DONE;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final InvoiceService invoiceService;

    @RabbitListener(queues = "#{paymentQueue.name}")
    void receiveInvoiceEvent(PaymentEvent event) {
        if (event.getStatus() == DONE) {
            InvoiceEntity invoiceEntity = invoiceService.updateInvoiceStatus(event.getPayment().getInvoiceId());
            log.info("Invoice Updated: " + invoiceEntity);
        }
    }
}
