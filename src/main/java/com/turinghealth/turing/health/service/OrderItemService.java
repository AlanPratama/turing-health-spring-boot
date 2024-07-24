package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.meta.OrderItem;
import com.turinghealth.turing.health.utils.dto.productDTO.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    OrderItem create(OrderItemDTO request);
    OrderItem getOne(Integer id);
    List<OrderItem> getAll();
    OrderItem update(OrderItemDTO request, Integer id);
    void deleteById(Integer id);
    
}
