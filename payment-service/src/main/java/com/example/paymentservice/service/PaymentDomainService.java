package com.example.paymentservice.service;

import com.example.paymentservice.dto.OrderCreatedEvent;
import com.example.paymentservice.dto.PaymentCompletedEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mock payment processor. In production, integrate with a real gateway.
 * This service implements idempotency via an in-memory map for demo.
 */
@Service
public class PaymentDomainService {

    // For demo only — replace with persistent store for production idempotency.
    private final ConcurrentHashMap<String, String> processed = new ConcurrentHashMap<>();

    public PaymentCompletedEvent processPayment(OrderCreatedEvent event) {
        // Use orderId as idempotency key
        var orderId = event.orderId();

        // If already processed, return previous result
        if (processed.containsKey(orderId)) {
            return new PaymentCompletedEvent(orderId, "SUCCESS", processed.get(orderId), Instant.now());
        }

        // Simulate payment processing (always succeed here). Add delays if needed to
        // simulate latency.
        var transactionId = UUID.randomUUID().toString();
        processed.put(orderId, transactionId);

        return new PaymentCompletedEvent(orderId, "SUCCESS", transactionId, Instant.now());
    }
}
