package com.teleport.must.have.skills.one_three.repository;

import com.teleport.must.have.skills.one_three.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    List<Order> findAll();
}
