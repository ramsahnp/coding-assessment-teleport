package com.teleport.must.have.skills.one_three.service;

import com.teleport.must.have.skills.one_three.entity.Customer;
import com.teleport.must.have.skills.one_three.entity.Order;
import com.teleport.must.have.skills.one_three.repository.CustomerRepository;
import com.teleport.must.have.skills.one_three.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private OrderRepository orderRepo;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder_NewCustomer() {
        String customerName = "Alice";
        String productName = "Laptop";

        when(customerRepo.findByName(customerName)).thenReturn(Optional.empty());

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName(customerName);

        when(customerRepo.save(any(Customer.class))).thenReturn(savedCustomer);

        customerService.placeOrder(customerName, productName);

        verify(customerRepo).findByName(customerName);
        verify(customerRepo).save(any(Customer.class));
        verify(orderRepo).save(any(Order.class));
    }

    @Test
    void testPlaceOrder_ExistingCustomer() {
        String customerName = "Bob";
        String productName = "Phone";

        Customer existingCustomer = new Customer();
        existingCustomer.setId(2L);
        existingCustomer.setName(customerName);

        when(customerRepo.findByName(customerName)).thenReturn(Optional.of(existingCustomer));

        customerService.placeOrder(customerName, productName);

        verify(customerRepo).findByName(customerName);
        verify(customerRepo, never()).save(any(Customer.class)); // Should not save again
        verify(orderRepo).save(any(Order.class));
    }

    @Test
    void testGetCustomersWithOrders() {
        String name = "Charlie";

        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer());

        when(customerRepo.findCustomersWithOrders(name)).thenReturn(mockCustomers);

        List<Customer> result = customerService.getCustomersWithOrders(name);

        assertEquals(1, result.size());
        verify(customerRepo).findCustomersWithOrders(name);
    }

    @Test
    void testGetAllOrders() {
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());
        when(orderRepo.findAll()).thenReturn(mockOrders);

        List<Order> result = customerService.getAllOrder();

        assertEquals(2, result.size());
        verify(orderRepo).findAll();
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> mockCustomers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepo.findAll()).thenReturn(mockCustomers);

        List<Customer> result = customerService.getAllCustomer();

        assertEquals(3, result.size());
        verify(customerRepo).findAll();
    }
}
