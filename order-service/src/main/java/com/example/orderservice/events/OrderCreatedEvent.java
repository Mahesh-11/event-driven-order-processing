package com.example.orderservice.events;

import java.time.Instant;

public record OrderCreatedEvent(String orderId,String customerId,String productId,int quantity,Instant createdAt){}