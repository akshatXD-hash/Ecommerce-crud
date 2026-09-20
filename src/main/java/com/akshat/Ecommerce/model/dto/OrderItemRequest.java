package com.akshat.Ecommerce.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {
}
