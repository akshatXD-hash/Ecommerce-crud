package com.akshat.Ecommerce.services;

import com.akshat.Ecommerce.model.Order;
import com.akshat.Ecommerce.model.OrderItem;
import com.akshat.Ecommerce.model.Product;
import com.akshat.Ecommerce.model.dto.OrderItemRequest;
import com.akshat.Ecommerce.model.dto.OrderItemResponse;
import com.akshat.Ecommerce.model.dto.OrderRequest;
import com.akshat.Ecommerce.model.dto.OrderResponse;
import com.akshat.Ecommerce.repo.OrderRepo;
import com.akshat.Ecommerce.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Transactional(readOnly = true)
    public  List<OrderResponse> getAllOrderResponses() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();



        for(Order order:orders){
            List<OrderItemResponse> itemResponses = new ArrayList<>();
            for(OrderItem item:order.getItems()){
                OrderItemResponse orderItemResponse = new OrderItemResponse(item.getProduct().getName(),
                item.getQuantity(),
                item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }



            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getCustomerEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }
       return orderResponses;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
       Order order = new Order();
       String orderId = "ORD"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
       order.setOrderId(orderId);
       order.setCustomerName(request.customerName());
       order.setCustomerEmail(request.customerEmail());
       order.setStatus("PLACED");
       order.setOrderDate(LocalDate.now());

       List<OrderItem> orderItems = new ArrayList<>();

       for(OrderItemRequest itemReq: request.items()){
           if (itemReq.quantity() <= 0) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive");
           }
           Product product = productRepo.findById(itemReq.productId())
                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

           if (!product.isAvailable() || product.getStockQuantity() < itemReq.quantity()) {
               throw new ResponseStatusException(HttpStatus.CONFLICT, "Product is unavailable or has insufficient stock");
           }

           product.setStockQuantity(product.getStockQuantity()-itemReq.quantity());
           productRepo.save(product);

           OrderItem orderItem = OrderItem.builder()
                   .product(product)
                   .quantity(itemReq.quantity())
                   .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
                   .order(order)
                   .build();

           orderItems.add(orderItem);

       }
       order.setItems(orderItems);

       List<OrderItemResponse> itemResponses = new ArrayList<>();
       for(OrderItem item:order.getItems()){
           OrderItemResponse orderItemResponse = new OrderItemResponse(
                   item.getProduct().getName(),
                   item.getQuantity(),
                   item.getTotalPrice()
           );
           itemResponses.add(orderItemResponse);
       }

       order.setItems(orderItems);
       Order savedOrder =  orderRepo.save(order);
       OrderResponse orderResponse = new OrderResponse(savedOrder.getOrderId(),savedOrder.getCustomerName(),savedOrder.getCustomerEmail(),
               savedOrder.getStatus(),savedOrder.getOrderDate(),itemResponses);

       return orderResponse;
    }
}
