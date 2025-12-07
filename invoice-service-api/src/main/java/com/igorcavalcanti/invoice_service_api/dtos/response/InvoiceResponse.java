package com.igorcavalcanti.invoice_service_api.dtos.response;

import com.igorcavalcanti.invoice_service_api.entity.InvoiceStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {

    private Long id;

    private Long clientId;
    private String clientName;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    private BigDecimal totalHours;
    private BigDecimal totalAmount;

    private InvoiceStatus status;

    private List<InvoiceItemResponse> items;
}
