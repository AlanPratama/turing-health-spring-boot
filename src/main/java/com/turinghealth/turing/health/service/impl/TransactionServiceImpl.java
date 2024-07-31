package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.entity.meta.transaction.OrderDetail;
import com.turinghealth.turing.health.entity.meta.transaction.OrderItem;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.OrderDetailRepository;
import com.turinghealth.turing.health.repository.OrderItemRepository;
import com.turinghealth.turing.health.repository.ProductRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.AddressUserService;
import com.turinghealth.turing.health.service.MidtransService;
import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.service.TransactionService;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.midtransDTO.BankTransferDTO;
import com.turinghealth.turing.health.utils.dto.midtransDTO.MidtransRequestDTO;
import com.turinghealth.turing.health.utils.dto.midtransDTO.TransactionDetailDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionRequestDTO;
import com.turinghealth.turing.health.utils.mapper.UserMapper;
import com.turinghealth.turing.health.utils.response.MidtransResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final AddressUserService addressUserService;
    private final MidtransService midtransService;

    @Override
    public TransactionDTO create(TransactionRequestDTO request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));


        // resiCode -> after user pay

        Integer total = 0;
        // CALCULATE TOTAL PRICE OF PRODUCTS
        for (Integer prId : request.getProducts()) {
            Product product = productService.getOne(prId);
            total += product.getPrice();
        }

        OrderDetail orderDetailTemp = OrderDetail.builder()
                .total(total)
                .message(request.getMessage())
                .paymentType(request.getPaymentType())
                .addressUser(addressUserService.getOne(request.getAddressId()))
                .user(user)
                .orderItems(null)
                .resiCode(null)
                .vaNumber(null)
                .expiryTime(null)
                .status(TransactionStatus.PENDING)
                .createdAt(new Date())
                .build();

        OrderDetail orderDetail = orderDetailRepository.save(orderDetailTemp);

        TransactionDetailDTO transactionDetailDTO = TransactionDetailDTO.builder()
                .order_id(String.valueOf(orderDetail.getId()))
                .gross_amount(total)
                .build();

        BankTransferDTO bankTransferDTO = BankTransferDTO.builder()
                .bank(request.getPaymentType())
                .build();

        MidtransRequestDTO midtransRequestDTO = MidtransRequestDTO.builder()
                .payment_type("bank_transfer")
                .transaction_details(transactionDetailDTO)
                .bank_transfer(bankTransferDTO)
                .build();

        MidtransResponse midtransResponse = midtransService.chargeTransaction(midtransRequestDTO);

        if (Objects.equals(midtransResponse.getStatusCode(), "201")) {
            // resiCode

            List<MidtransResponse.VaNumber> vaNumber = midtransResponse.getVaNumbers();

            for (var v : vaNumber) {
                orderDetail.setVaNumber(v.getVaNumber());
            }

            orderDetail.setExpiryTime(midtransResponse.getExpiryTime());

            List<OrderItem> orderItems = new ArrayList<>();
            List<Product> productList = new ArrayList<>();

            // PRODUCT NEED TO PUTTED INTO ORDER ITEM
            for (Integer prId : request.getProducts()) {
                Product product = productService.getOne(prId);
                productList.add(product);

                OrderItem orderItemTemp = OrderItem.builder()
                        .createdAt(new Date())
                        .orderDetail(orderDetail)
                        .product(product)
                        .build();

                OrderItem orderItem = orderItemRepository.save(orderItemTemp);

                orderItems.add(orderItem);
            }

            orderDetail.setOrderItems(orderItems);

            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);

            return TransactionDTO.builder()
                    .user(UserMapper.accountDTO(updatedOrderDetail.getUser()))
                    .addressUser(updatedOrderDetail.getAddressUser())
                    .total(updatedOrderDetail.getTotal())
                    .message(updatedOrderDetail.getMessage())
                    .paymentType(updatedOrderDetail.getPaymentType())
                    .resiCode(updatedOrderDetail.getResiCode())
                    .vaNumber(updatedOrderDetail.getVaNumber())
                    .expiryTime(updatedOrderDetail.getExpiryTime())
                    .status(updatedOrderDetail.getStatus())
                    .createdAt(updatedOrderDetail.getCreatedAt())
                    .products(productList)
                    .build();
        }

        return null;
    }

    @Override
    public TransactionDTO changeStatusToPacked(String orderId) {
        MidtransResponse midtrans = midtransService.fetchTransaction(orderId);
        OrderDetail orderDetail = orderDetailRepository.findById(Integer.parseInt(orderId)).orElseThrow(() -> new NotFoundException("Transaction Not Found!"));


        OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
        List<Product> productList = updatedOrderDetail.getOrderItems().stream()
                .map(OrderItem::getProduct)
                .collect(Collectors.toList());

        return TransactionDTO.builder()
                .user(UserMapper.accountDTO(updatedOrderDetail.getUser()))
                .addressUser(updatedOrderDetail.getAddressUser())
                .total(updatedOrderDetail.getTotal())
                .message(updatedOrderDetail.getMessage())
                .paymentType(updatedOrderDetail.getPaymentType())
                .resiCode(updatedOrderDetail.getResiCode())
                .vaNumber(updatedOrderDetail.getVaNumber())
                .expiryTime(updatedOrderDetail.getExpiryTime())
                .status(updatedOrderDetail.getStatus())
                .createdAt(updatedOrderDetail.getCreatedAt())
                .products(productList)
                .build();
    }

    @Override
    public TransactionDTO changeStatusToSent(String orderId, String resiCode) {
        return null;
    }

    @Override
    public TransactionDTO changeStatusToAccepted(String orderId) {
        return null;
    }
}
