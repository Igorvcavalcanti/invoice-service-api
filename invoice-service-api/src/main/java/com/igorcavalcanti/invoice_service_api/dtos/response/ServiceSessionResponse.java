package com.igorcavalcanti.invoice_service_api.dtos.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSessionResponse {

    private Long id;
    private Long clientId;
    private String clientName;
    private LocalDate date;
    private BigDecimal hoursWorked;
    private String description;
}
