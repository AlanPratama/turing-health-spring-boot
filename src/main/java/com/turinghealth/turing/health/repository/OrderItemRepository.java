package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.transaction.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
}
