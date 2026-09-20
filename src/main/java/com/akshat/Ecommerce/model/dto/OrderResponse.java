package com.akshat.Ecommerce.model.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        String orderId,
        String customerName,
        String customerEmail,
        String status,
        LocalDate orderDate,
        List<OrderItemResponse> items
) {
}
