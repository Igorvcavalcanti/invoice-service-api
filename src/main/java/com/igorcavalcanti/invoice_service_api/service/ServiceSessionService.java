package com.igorcavalcanti.invoice_service_api.service;

import com.igorcavalcanti.invoice_service_api.dtos.request.CreateServiceSessionRequest;
import com.igorcavalcanti.invoice_service_api.dtos.response.ServiceSessionResponse;
import com.igorcavalcanti.invoice_service_api.entity.Client;
import com.igorcavalcanti.invoice_service_api.entity.ServiceSession;
import com.igorcavalcanti.invoice_service_api.repository.ClientRepository;
import com.igorcavalcanti.invoice_service_api.repository.ServiceSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceSessionService {

    private final ServiceSessionRepository serviceSessionRepository;
    private final ClientRepository clientRepository;

    public ServiceSessionResponse create(CreateServiceSessionRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Client not foud with id: " + request.getClientId()
                ));

        ServiceSession session = ServiceSession.builder()
                .client(client)
                .date(request.getDate())
                .hoursWroked(request.getHoursWorked())
                .description(request.getDescription())
                .build();

        ServiceSession saved = serviceSessionRepository.save(session);
        return toResponse(saved);

    }

    public List<ServiceSessionResponse> findByClient(Long clientId) {
        return serviceSessionRepository.findByClientId(clientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ServiceSessionResponse> findByClientAndPeriod(Long clientId, LocalDate start, LocalDate end) {
        return serviceSessionRepository
                .findByClientIdAndDateBetween(clientId, start, end)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ServiceSessionResponse toResponse(ServiceSession session) {
        return ServiceSessionResponse.builder()
                .id(session.getId())
                .clientId(session.getClient().getId())
                .clientName(session.getClient().getName())
                .date(session.getDate())
                .hoursWorked(session.getHoursWroked())
                .description(session.getDescription())
                .build();
    }
}
