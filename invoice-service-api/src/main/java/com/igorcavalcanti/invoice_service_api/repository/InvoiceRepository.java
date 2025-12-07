package com.igorcavalcanti.invoice_service_api.repository;

import com.igorcavalcanti.invoice_service_api.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByClientId(Long clientId);
}
