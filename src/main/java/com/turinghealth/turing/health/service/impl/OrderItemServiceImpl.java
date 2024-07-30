package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.OrderItem;

import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.entity.meta.transaction.OrderDetail;
import com.turinghealth.turing.health.repository.OrderItemRepository;
import com.turinghealth.turing.health.service.OrderItemService;
import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.utils.dto.orderItemDTO.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    @Override
    public OrderItem create(OrderItemDTO request) {
        Product product = productService.getOne(request.getProductId());
        OrderDetail orderDetail = request.getOrderDetail();
        return orderItemRepository.save(OrderItem.builder()
                        .orderDetail(orderDetail)
                        .product(product)
                        .createdAt(request.getCreateAt())
                .build());
    }

    @Override
    public OrderItem getOne(Integer id) {
        return orderItemRepository.findById(id).orElseThrow(()-> new RuntimeException("Order Item not found"));
    }

    @Override
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem update(OrderItemDTO request, Integer id) {
        OrderItem orderItem = this.getOne(id);
        orderItem.setCreatedAt(request.getCreateAt());
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}
