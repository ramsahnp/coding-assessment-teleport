package com.teleport.must.have.skills.one_three.controller;

import com.teleport.must.have.skills.one_three.entity.Customer;
import com.teleport.must.have.skills.one_three.entity.Order;
import com.teleport.must.have.skills.one_three.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestParam String name, @RequestParam String product) {
        customerService.placeOrder(name, product);
        return ResponseEntity.ok("Order placed");
    }

    @GetMapping("/all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(customerService.getAllOrder());
    }

    @GetMapping("/all-customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/with-orders")
    public ResponseEntity<List<Customer>> getWithOrders(@RequestParam String name) {
        return ResponseEntity.ok(customerService.getCustomersWithOrders(name));
    }

    @GetMapping(value = "/orders/stream", produces = "text/event-stream")
    public Flux<Order> streamOrders() {
        return customerService.streamOrders();
    }

}
