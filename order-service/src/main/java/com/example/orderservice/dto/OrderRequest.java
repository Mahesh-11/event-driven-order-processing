package com.example.orderservice.dto;

public record OrderRequest(String customerId,String productId,int quantity){}