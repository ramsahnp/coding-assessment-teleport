package com.teleport.must.have.skills.one_three.repository;

import com.teleport.must.have.skills.one_three.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT DISTINCT c FROM Customer c JOIN c.orders o WHERE c.name = :name")
    List<Customer> findCustomersWithOrders(@Param("name") String name);

    @Override
    List<Customer> findAll();

    @Query("SELECT c FROM Customer c where c.name=:name")
    Optional<Customer> findByName(String name);
}
