package com.igorcavalcanti.invoice_service_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "service_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cliente atendido
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    //Data do servico
    @Column(nullable = false)
    private LocalDate date;

    //Numero de horas trabalhadas nesse dia
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal hoursWroked;

    // Ex.: "Weekly Cleaning", "Garden maintenance"
    @Column(nullable = false)
    private String description;
}
