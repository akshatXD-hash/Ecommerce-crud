package com.akshat.Ecommerce.controllers;

import com.akshat.Ecommerce.model.dto.OrderRequest;
import com.akshat.Ecommerce.model.dto.OrderResponse;
import com.akshat.Ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){


        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);

    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>>  getAllOrderResponses(){
        List<OrderResponse> orders = orderService.getAllOrderResponses();
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
