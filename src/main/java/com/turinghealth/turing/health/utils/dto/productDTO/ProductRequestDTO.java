package com.turinghealth.turing.health.utils.dto.productDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @JsonProperty("brand_generic")
    private String brandGeneric;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("form")
    private String form;

    @JsonProperty("insurance_eligible")
    private String insuranceEligible;

    @JsonProperty("medication_name")
    private String medicationName;

    @JsonProperty("ndc")
    private String ndc;

    @JsonProperty("pill_nonpill")
    private String pillNonPill;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("special_handling")
    private String specialHandling;

    @JsonProperty("strength")
    private String strength;

    @JsonProperty("unit_billing_price")
    private Double unitBillingPrice;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("url")
    private String url;


}
