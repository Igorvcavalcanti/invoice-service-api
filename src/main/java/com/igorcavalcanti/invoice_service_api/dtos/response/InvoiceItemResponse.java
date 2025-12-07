package com.igorcavalcanti.invoice_service_api.dtos.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemResponse {

    private Long sessionId;
    private LocalDate date;
    private BigDecimal hoursWorked;
    private String description;
}
