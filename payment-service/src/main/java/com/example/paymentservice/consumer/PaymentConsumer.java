package com.example.paymentservice.consumer;

import com.example.paymentservice.dto.OrderCreatedEvent;
import com.example.paymentservice.dto.PaymentCompletedEvent;
import com.example.paymentservice.producer.PaymentProducer;
import com.example.paymentservice.service.PaymentDomainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {
    private final PaymentDomainService paymentDomainService;
    private final PaymentProducer paymentProducer;
    private final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);

    public PaymentConsumer(PaymentDomainService paymentDomainService, PaymentProducer paymentProducer) {
        this.paymentDomainService = paymentDomainService;
        this.paymentProducer = paymentProducer;
    }

    @KafkaListener(topics = "orders.created", groupId = "payment-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload OrderCreatedEvent event) {
        log.info("Received order.created event for orderId={}", event.orderId());
        try {
            PaymentCompletedEvent paymentEvent = paymentDomainService.processPayment(event);
            paymentProducer.publishPaymentCompleted(paymentEvent);
            log.info("Payment processed for orderId={} status={}", event.orderId(), paymentEvent.status());
        } catch (Exception e) {
            log.error("Failed to process payment for orderId={}", event.orderId(), e);
            // here you could publish to a retry topic / DLQ
        }
    }
}
