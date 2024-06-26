package com.enigma.wmbapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    @JsonProperty("transaction_details")
    private PaymentDetailRequest paymentDetail;
    @JsonProperty("item_details")
    private List<PaymentItemDetailRequest> paymentItemDetails;
    @JsonProperty("enable_payments")
    private  List<String> paymentMethod;
}
