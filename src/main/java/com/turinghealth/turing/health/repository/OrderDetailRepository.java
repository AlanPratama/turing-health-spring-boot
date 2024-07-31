package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.transaction.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
