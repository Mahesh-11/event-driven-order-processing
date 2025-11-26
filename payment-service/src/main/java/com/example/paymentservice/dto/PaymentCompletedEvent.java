package com.example.paymentservice.dto;

import java.time.Instant;

public record PaymentCompletedEvent(String orderId, String status, String transactionId, Instant processedAt) {
}
