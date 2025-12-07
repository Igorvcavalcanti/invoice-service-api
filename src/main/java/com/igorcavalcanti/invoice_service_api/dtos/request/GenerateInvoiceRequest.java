package com.igorcavalcanti.invoice_service_api.dtos.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateInvoiceRequest {

    @NotNull(message = "Client id is required")
    private Long clientId;

    @NotNull(message = "Period start is required")
    private LocalDate periodStart;

    @NotNull(message = "Period end is required")
    private LocalDate periodEnd;
}
