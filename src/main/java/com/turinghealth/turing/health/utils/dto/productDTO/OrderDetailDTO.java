package com.turinghealth.turing.health.utils.dto.productDTO;

import com.turinghealth.turing.health.entity.enums.Status;
import com.turinghealth.turing.health.entity.meta.PaymentDetail;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private Integer paymentId;
    private Integer total;
    private Status status;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private PaymentDetail paymentDetail;
}
