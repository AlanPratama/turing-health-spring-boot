package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.meta.transaction.OrderDetail;
import com.turinghealth.turing.health.utils.dto.orderDetailDTO.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    OrderDetail create(OrderDetailDTO request);
    OrderDetail getOne(Integer id);
    List<OrderDetail> getAll();
    OrderDetail update(Integer id, OrderDetailDTO request);
    void deleteById(Integer id);
}
