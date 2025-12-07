package com.igorcavalcanti.invoice_service_api.repository;

import com.igorcavalcanti.invoice_service_api.entity.ServiceSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ServiceSessionRepository extends JpaRepository<ServiceSession, Long> {

    List<ServiceSession> findByClientId(Long clientId);

    List<ServiceSession> findByClientIdAndDateBetween(Long clientId, LocalDate startDate, LocalDate endDate);

}
