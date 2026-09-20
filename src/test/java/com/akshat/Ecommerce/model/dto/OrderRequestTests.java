package com.akshat.Ecommerce.model.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTests {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void createValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        factory.close();
    }

    @Test
    void acceptsValidCheckout() {
        assertTrue(validator.validate(new OrderRequest("Akshat", "akshat@example.com",
                List.of(new OrderItemRequest(5, 2)))).isEmpty());
    }

    @Test
    void rejectsMissingCustomerDetailsAndMalformedEmail() {
        for (String email : Arrays.asList(null, "", "invalid-email")) {
            var errors = validator.validate(new OrderRequest(" ", email,
                    List.of(new OrderItemRequest(5, 2))));
            assertTrue(errors.stream().anyMatch(e -> e.getPropertyPath().toString().equals("customerName")));
            assertTrue(errors.stream().anyMatch(e -> e.getPropertyPath().toString().equals("customerEmail")));
        }
    }

    @Test
    void rejectsMissingOrEmptyItems() {
        assertFalse(validator.validate(new OrderRequest("Akshat", "akshat@example.com", null)).isEmpty());
        assertFalse(validator.validate(new OrderRequest("Akshat", "akshat@example.com", List.of())).isEmpty());
    }

    @Test
    void validatesNestedItemsAndRejectsNullEntries() {
        assertFalse(validator.validate(new OrderRequest("Akshat", "akshat@example.com",
                Arrays.asList((OrderItemRequest) null))).isEmpty());
        var errors = validator.validate(new OrderRequest("Akshat", "akshat@example.com",
                List.of(new OrderItemRequest(0, -1))));
        assertEquals(2, errors.size());
    }
}
