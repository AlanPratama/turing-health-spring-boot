package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.repository.OrderDetailRepository;
import com.turinghealth.turing.health.repository.OrderItemRepository;
import com.turinghealth.turing.health.repository.ProductRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.TransactionServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Mock
    private AddressUserService addressUserService;

    @Mock
    private MidtransService midtransService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;
}
