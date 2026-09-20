package com.akshat.Ecommerce.services;

import com.akshat.Ecommerce.model.Order;
import com.akshat.Ecommerce.model.Product;
import com.akshat.Ecommerce.model.dto.OrderItemRequest;
import com.akshat.Ecommerce.model.dto.OrderRequest;
import com.akshat.Ecommerce.repo.OrderRepo;
import com.akshat.Ecommerce.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {
    @Mock private ProductRepo productRepo;
    @Mock private OrderRepo orderRepo;
    @InjectMocks private OrderService service;
    private Product keyboard;

    @BeforeEach
    void setUp() {
        keyboard = new Product();
        keyboard.setId(5);
        keyboard.setName("Keyboard");
        keyboard.setPrice(new BigDecimal("1000.00"));
        keyboard.setAvailable(true);
        keyboard.setStockQuantity(5);
    }

    private OrderRequest request(int quantity) {
        return new OrderRequest("Akshat", "akshat@example.com",
                List.of(new OrderItemRequest(5, quantity)));
    }

    @Test
    void checkoutUsesProductPriceAndConnectsBothSidesOfTheOrder() {
        when(productRepo.findById(5)).thenReturn(Optional.of(keyboard));
        when(orderRepo.save(any(Order.class))).thenAnswer(call -> call.getArgument(0));

        var response = service.placeOrder(request(2));

        assertEquals(3, keyboard.getStockQuantity());
        assertEquals(new BigDecimal("2000.00"), response.items().getFirst().totalPrice());
        assertEquals("Keyboard", response.items().getFirst().productName());
        assertEquals("PLACED", response.status());
        var captured = ArgumentCaptor.forClass(Order.class);
        verify(orderRepo).save(captured.capture());
        Order saved = captured.getValue();
        assertSame(saved, saved.getItems().getFirst().getOrder());
    }

    @Test
    void rejectsInsufficientStockWithoutSaving() {
        when(productRepo.findById(5)).thenReturn(Optional.of(keyboard));
        assertRejected(6, HttpStatus.CONFLICT);
    }

    @Test
    void rejectsUnavailableProductsWithoutSaving() {
        keyboard.setAvailable(false);
        when(productRepo.findById(5)).thenReturn(Optional.of(keyboard));
        assertRejected(1, HttpStatus.CONFLICT);
    }

    @Test
    void returnsNotFoundForMissingProducts() {
        when(productRepo.findById(5)).thenReturn(Optional.empty());
        assertRejected(1, HttpStatus.NOT_FOUND);
    }

    @Test
    void rejectsNonpositiveQuantitiesBeforeLookingUpProducts() {
        assertRejected(0, HttpStatus.BAD_REQUEST);
        assertRejected(-1, HttpStatus.BAD_REQUEST);
        verifyNoInteractions(productRepo);
    }

    private void assertRejected(int quantity, HttpStatus status) {
        var error = assertThrows(ResponseStatusException.class,
                () -> service.placeOrder(request(quantity)));
        assertEquals(status, error.getStatusCode());
        assertEquals(5, keyboard.getStockQuantity());
        verify(productRepo, never()).save(any());
        verifyNoInteractions(orderRepo);
    }
}
