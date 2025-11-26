package com.example.paymentservice.producer;

import com.example.paymentservice.dto.PaymentCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(PaymentProducer.class);
    private static final String TOPIC = "orders.paid";

    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Publishing payment completed for orderId={} tx={}", event.orderId(), event.transactionId());
        kafkaTemplate.send(TOPIC, event.orderId(), event);
    }
}
