package com.igorcavalcanti.invoice_service_api.repository;

import com.igorcavalcanti.invoice_service_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
