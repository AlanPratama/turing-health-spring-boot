package com.turinghealth.turing.health.utils.dto.addressUserDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUserRequestDTO {
    @NotBlank(message = "Buyer Name Cannot Be Null!")
    private String buyerName;

    @NotBlank(message = "Buyer Phone Cannot Be Null!")
    private String buyerPhone;

    @NotBlank(message = "City Cannot Be Null!")
    private String city;

    @NotBlank(message = "Region Cannot Be Null!")
    private String region;

    @NotBlank(message = "Pos Code Cannot Be Null!")
    private String posCode;

    @NotBlank(message = "Address Detail Cannot Be Null!")
    private String addressDetail;

    private String fixPoint;

    @NotBlank(message = "Type Cannot Be Null!")
    private String type;

    private String message;
}
