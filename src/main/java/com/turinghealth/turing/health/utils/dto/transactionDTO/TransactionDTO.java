package com.turinghealth.turing.health.utils.dto.transactionDTO;

import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.entity.meta.transaction.AddressUser;
import com.turinghealth.turing.health.utils.dto.consultationDTO.AccountDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class TransactionDTO {

    private AccountDTO user;
    private AddressUser addressUser;
    private Integer total;
    private String message;
    private String paymentType;
    private String resiCode;
    private String vaNumber;
    private String expiryTime;
    private TransactionStatus status;
    private Date createdAt;

    List<Product> products;

}
