package com.teleport.must.have.skills.one_three.controller;


import com.teleport.must.have.skills.one_three.entity.Customer;
import com.teleport.must.have.skills.one_three.entity.Order;
import com.teleport.must.have.skills.one_three.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void testPlaceOrder() throws Exception {
        mockMvc.perform(post("/api/customers/order")
                        .param("name", "John")
                        .param("product", "Book"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed"));

        verify(customerService).placeOrder("John", "Book");
    }

    @Test
    void testGetAllOrders() throws Exception {
        List<Order> mockOrders = List.of(new Order(1L, "Book", null));
        when(customerService.getAllOrder()).thenReturn(mockOrders);

        mockMvc.perform(get("/api/customers/all-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product").value("Book"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<Customer> mockCustomers = List.of(new Customer(1L, "Alice", new ArrayList<>()));
        when(customerService.getAllCustomer()).thenReturn(mockCustomers);

        mockMvc.perform(get("/api/customers/all-customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void testGetWithOrders() throws Exception {
        List<Customer> mockCustomers = List.of(new Customer(1L, "Bob", new ArrayList<>()));
        when(customerService.getCustomersWithOrders("Bob")).thenReturn(mockCustomers);

        mockMvc.perform(get("/api/customers/with-orders").param("name", "Bob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob"));
    }


}
