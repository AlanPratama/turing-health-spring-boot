package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.transaction.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
}
