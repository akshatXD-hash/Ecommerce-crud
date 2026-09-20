package com.akshat.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Long id;
    @Column(unique = true)
    private String orderId;
    private String customerName;
    private String customerEmail;
    private String status;
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items;


}
