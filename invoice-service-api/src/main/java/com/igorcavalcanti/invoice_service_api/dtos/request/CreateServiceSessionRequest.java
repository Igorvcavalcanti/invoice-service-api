package com.igorcavalcanti.invoice_service_api.dtos.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateServiceSessionRequest {

    @NotNull(message = "Client id is required")
    private Long clientId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Hours worked is required")
    @DecimalMin(value = "0.1", inclusive = true, message = "Hours worked must be greater than zero")
    private BigDecimal hoursWorked;

    @NotBlank(message = "Description is required")
    private String description;
}
