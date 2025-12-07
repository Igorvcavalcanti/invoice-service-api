package com.igorcavalcanti.invoice_service_api.dtos.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private BigDecimal hourlyRate;
}
