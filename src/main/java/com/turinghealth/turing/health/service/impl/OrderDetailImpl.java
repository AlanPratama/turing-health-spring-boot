package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.OrderDetail;
import com.turinghealth.turing.health.service.OrderDetailService;
import com.turinghealth.turing.health.utils.dto.orderDetailDTO.OrderDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailImpl implements OrderDetailService {
    @Override
    public OrderDetail create(OrderDetailDTO request) {
        return null;
    }

    @Override
    public OrderDetail getOne(Integer id) {
        return null;
    }

    @Override
    public List<OrderDetail> getAll() {
        return List.of();
    }

    @Override
    public OrderDetail update(Integer id, OrderDetailDTO request) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
