package com.teleport.must.have.skills.one_three.service;

import com.teleport.must.have.skills.one_three.entity.Customer;
import com.teleport.must.have.skills.one_three.entity.Order;
import com.teleport.must.have.skills.one_three.kafka.UserEventProducer;
import com.teleport.must.have.skills.one_three.repository.CustomerRepository;
import com.teleport.must.have.skills.one_three.repository.OrderRepository;
import com.teleport.must.have.skills.one_three.util.KafkaUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;
    private final String[] statuses = {"CREATED", "PACKING", "SHIPPED", "DELIVERED"};

    private final UserEventProducer userEventProducer;

    @Autowired
    public CustomerService(CustomerRepository customerRepo, OrderRepository orderRepo, UserEventProducer userEventProducer) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.userEventProducer = userEventProducer;
    }

    @Transactional
    public void placeOrder(String customerName, String productName) {
        try {
            Optional<Customer> customerFromDb = customerRepo.findByName(customerName);

            Customer customer;
            if (customerFromDb.isPresent()) {
                customer = customerFromDb.get();
                logger.info("Old Customer, customer name: {}", customer.getName());
            } else {
                customer = new Customer();
                customer.setName(customerName);
                customer = customerRepo.save(customer);  // Save only if customer doesn't exist
                logger.info("New customer, customer name: {}", customer.getName());
                //userEvent.setAction("new customer added, customer id: " + customer.getId());
            }
            Order order = new Order();
            order.setProduct(productName);
            order.setCustomer(customer);
            order = orderRepo.save(order);
            //userEvent.setAction("new oder added, order id: " + order.getId());
            userEventProducer.sendMessage("Hello...Consumers");
            logger.info("Order Placed Successfully..!");
        } catch (Exception ex) {
            logger.info("Error while placing order error-msg: {} ", ex.getMessage());
            ex.printStackTrace();
        }
    }


    public List<Customer> getCustomersWithOrders(String name) {
        return customerRepo.findCustomersWithOrders(name);
    }

    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }

    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    public Flux<Order> streamOrders() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(this::generateOrder)
                .take(20); // simulate 20 updates
    }

    private Order generateOrder(long index) {
        Random random = new Random();
        long orderId = random.nextLong();
        String status = statuses[(int) (index % statuses.length)];
        Customer customer = new Customer("customer name1");
        return new Order(orderId, "product-status"+status, customer);
    }

}
