package com.turinghealth.turing.health.utils.dto.orderItemDTO;

import com.turinghealth.turing.health.entity.meta.OrderDetail;
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
public class OrderItemDTO {
    private Integer productId;

    @Temporal(TemporalType.DATE)
    private Date createAt;

    private OrderDetail orderDetail;
}
