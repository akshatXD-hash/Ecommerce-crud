package com.akshat.Ecommerce.model.dto;

import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @Positive int productId,
        @Positive int quantity
) {
}
