package com.teleport.must.have.skills.one_three.service;

import com.teleport.must.have.skills.one_three.entity.Customer;
import com.teleport.must.have.skills.one_three.entity.Order;
import com.teleport.must.have.skills.one_three.repository.CustomerRepository;
import com.teleport.must.have.skills.one_three.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public CustomerService(CustomerRepository customerRepo, OrderRepository orderRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional
    public void placeOrder(String customerName, String productName) {
        Optional<Customer> customerFromDb = customerRepo.findByName(customerName);

        Customer customer;
        if (customerFromDb.isPresent()) {
            customer = customerFromDb.get();
        } else {
            customer = new Customer();
            customer.setName(customerName);
            customer = customerRepo.save(customer);  // Save only if customer doesn't exist
        }

        Order order = new Order();
        order.setProduct(productName);
        order.setCustomer(customer);
        orderRepo.save(order);
    }


    public List<Customer> getCustomersWithOrders(String name) {
        return customerRepo.findCustomersWithOrders(name);
    }

    public List<Order> getAllOrder(){
        return orderRepo.findAll();
    }

    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

}
