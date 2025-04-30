package com.javatechie.webflux.dao;

import com.javatechie.webflux.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);
    private static void sleepExecution(int i){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers()  {
        return IntStream.rangeClosed(1, 10)
                .peek(CustomerDao::sleepExecution)
                .peek(i -> logger.info("processing count : {}" , i))
                .mapToObj(i -> new Customer(i, "customer" + i))
                .collect(Collectors.toList());
    }


    public Flux<Customer> getCustomersStream()  {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> logger.info("processing count in stream flow : {}", i))
                .map(i -> new Customer(i, "customer" + i));
    }


    public Flux<Customer> getCustomerList()  {
        return Flux.range(1,50)
                .doOnNext(i -> logger.info("processing count in stream flow : {}" , i))
                .map(i -> new Customer(i, "customer" + i));
    }
}
