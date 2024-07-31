package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.enums.Role;
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
import com.turinghealth.turing.health.utils.adviser.exception.ValidateException;
import com.turinghealth.turing.health.utils.dto.midtransDTO.BankTransferDTO;
import com.turinghealth.turing.health.utils.dto.midtransDTO.MidtransRequestDTO;
import com.turinghealth.turing.health.utils.dto.midtransDTO.TransactionDetailDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionRequestDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionResiCodeDTO;
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

        if (user.getRole() == Role.ADMIN) throw new ValidateException("Invalid Role!");

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
                    .id(updatedOrderDetail.getId())
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
    // ALL ROLE
    public TransactionDTO changeStatusToCanceled(String orderId) {
        MidtransResponse midtrans = midtransService.fetchTransaction(orderId);
        OrderDetail orderDetail = orderDetailRepository.findById(Integer.parseInt(orderId)).orElseThrow(() -> new NotFoundException("Transaction Not Found!"));

        if (Objects.equals(midtrans.getTransactionStatus(), "pending") && orderDetail.getStatus().equals(TransactionStatus.PENDING)) {
            midtransService.updateTransactionStatusToCanceled(orderId);

            orderDetail.setStatus(TransactionStatus.CANCELED);

            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
            List<Product> productList = updatedOrderDetail.getOrderItems().stream()
                    .map(OrderItem::getProduct)
                    .collect(Collectors.toList());

            return TransactionDTO.builder()
                    .id(updatedOrderDetail.getId())
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

        throw new ValidateException("Invalid Transaction Status!");
    }

    @Override
    // !: BUYER ALREADY PAY THE BILL
    // ALL ROLE
    public TransactionDTO changeStatusToPacked(String orderId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        MidtransResponse midtrans = midtransService.fetchTransaction(orderId);
        OrderDetail orderDetail = orderDetailRepository.findById(Integer.parseInt(orderId)).orElseThrow(() -> new NotFoundException("Transaction Not Found!"));

        if (Objects.equals(midtrans.getTransactionStatus(), "settlement")) {
            orderDetail.setStatus(TransactionStatus.PACKED);

            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
            List<Product> productList = updatedOrderDetail.getOrderItems().stream()
                    .map(OrderItem::getProduct)
                    .collect(Collectors.toList());

            return TransactionDTO.builder()
                    .id(updatedOrderDetail.getId())
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

        throw new ValidateException("Invalid Transaction Status!");
    }

    @Override
    // DOCTOR
    public TransactionDTO changeStatusToSent(String orderId, TransactionResiCodeDTO resiCode) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        UserMiddleware.isAdmin(user.getRole());

        MidtransResponse midtrans = midtransService.fetchTransaction(orderId);
        OrderDetail orderDetail = orderDetailRepository.findById(Integer.parseInt(orderId)).orElseThrow(() -> new NotFoundException("Transaction Not Found!"));

        if (Objects.equals(midtrans.getTransactionStatus(), "settlement") && orderDetail.getStatus().equals(TransactionStatus.PACKED)) {
            orderDetail.setResiCode(resiCode.getResiCode());
            orderDetail.setStatus(TransactionStatus.SENT);

            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
            List<Product> productList = updatedOrderDetail.getOrderItems().stream()
                    .map(OrderItem::getProduct)
                    .collect(Collectors.toList());

            return TransactionDTO.builder()
                    .id(updatedOrderDetail.getId())
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

        throw new ValidateException("Invalid Transaction Status!");
    }


    @Override
    // MEMBER
    // DOCTOR
    public TransactionDTO changeStatusToAccepted(String orderId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        if (user.getRole() == Role.ADMIN) throw new ValidateException("Invalid Role!");

        OrderDetail orderDetail = orderDetailRepository.findById(Integer.parseInt(orderId)).orElseThrow(() -> new NotFoundException("Transaction Not Found!"));
        MidtransResponse midtrans = midtransService.fetchTransaction(orderId);

        if (Objects.equals(midtrans.getTransactionStatus(), "settlement") && orderDetail.getStatus().equals(TransactionStatus.SENT)) {
            orderDetail.setStatus(TransactionStatus.ACCEPTED);

            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
            List<Product> productList = updatedOrderDetail.getOrderItems().stream()
                    .map(OrderItem::getProduct)
                    .collect(Collectors.toList());

            return TransactionDTO.builder()
                    .id(updatedOrderDetail.getId())
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

        throw new ValidateException("Invalid Transaction Status!");
    }

    @Override
    public List<TransactionDTO> getAllTransaction() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for (OrderDetail transaction : user.getOrderDetails()) {
            List<Product> productList = new ArrayList<>();

            for (OrderItem orderItem : transaction.getOrderItems()) {
                productList.add(orderItem.getProduct());
            }

            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transaction.getId());
            transactionDTO.setUser(UserMapper.accountDTO(transaction.getUser()));
            transactionDTO.setAddressUser(transaction.getAddressUser());
            transactionDTO.setTotal(transaction.getTotal());
            transactionDTO.setMessage(transaction.getMessage());
            transactionDTO.setPaymentType(transaction.getPaymentType());
            transactionDTO.setResiCode(transaction.getResiCode());
            transactionDTO.setVaNumber(transaction.getVaNumber());
            transactionDTO.setExpiryTime(transaction.getExpiryTime());
            transactionDTO.setStatus(transaction.getStatus());
            transactionDTO.setCreatedAt(transaction.getCreatedAt());
            transactionDTO.setProducts(productList);


            transactionDTOList.add(transactionDTO);
        }

        return transactionDTOList;
    }

    @Override
    public TransactionDTO getOneTransaction(String orderId) {
        OrderDetail transaction = orderDetailRepository.findById(Integer.parseInt(orderId)).orElseThrow(() -> new NotFoundException("Transaction Not Found!"));

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        if (user.getRole() != Role.ADMIN) {
            if (!Objects.equals(transaction.getUser().getId(), user.getId())) {
                throw new ValidateException("Transaction Is Not Your!");
            }
        }

        List<Product> productList = new ArrayList<>();

        for (var transactionItems : transaction.getOrderItems()) {
            productList.add(transactionItems.getProduct());
        }

        return TransactionDTO.builder()
                .id(transaction.getId())
                .user(UserMapper.accountDTO(transaction.getUser()))
                .addressUser(transaction.getAddressUser())
                .total(transaction.getTotal())
                .message(transaction.getMessage())
                .paymentType(transaction.getPaymentType())
                .resiCode(transaction.getResiCode())
                .vaNumber(transaction.getVaNumber())
                .expiryTime(transaction.getExpiryTime())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .products(productList)
                .build();
    }
}
