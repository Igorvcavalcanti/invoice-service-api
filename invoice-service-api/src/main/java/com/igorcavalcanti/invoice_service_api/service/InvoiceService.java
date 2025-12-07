package com.igorcavalcanti.invoice_service_api.service;


import com.igorcavalcanti.invoice_service_api.dtos.request.GenerateInvoiceRequest;
import com.igorcavalcanti.invoice_service_api.dtos.response.InvoiceItemResponse;
import com.igorcavalcanti.invoice_service_api.dtos.response.InvoiceResponse;
import com.igorcavalcanti.invoice_service_api.entity.Client;
import com.igorcavalcanti.invoice_service_api.entity.Invoice;
import com.igorcavalcanti.invoice_service_api.entity.InvoiceStatus;
import com.igorcavalcanti.invoice_service_api.entity.ServiceSession;
import com.igorcavalcanti.invoice_service_api.repository.ClientRepository;
import com.igorcavalcanti.invoice_service_api.repository.InvoiceRepository;
import com.igorcavalcanti.invoice_service_api.repository.ServiceSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ClientRepository clientRepository;
    private final ServiceSessionRepository serviceSessionRepository;
    private final InvoiceRepository invoiceRepository;

    public InvoiceResponse generateInvoice(GenerateInvoiceRequest request) {
        if (request.getPeriodEnd().isBefore(request.getPeriodStart())){
            throw new IllegalArgumentException("Period end must be after period start");
        }

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Client not found with id: " + request.getClientId()
                ));

        List<ServiceSession> sessions = serviceSessionRepository
                .findByClientIdAndDateBetween(
                        client.getId(),
                        request.getPeriodStart(),
                        request.getPeriodEnd()
                );
        if (sessions.isEmpty()) {
            throw new IllegalArgumentException("No service sessions found for this client and period");
        }

        BigDecimal totalHours = sessions.stream()
                .map(ServiceSession::getHoursWroked)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAmount = totalHours
                .multiply(client.getHourlyRate())
                .setScale(2, RoundingMode.HALF_UP);

        Invoice invoice = Invoice.builder()
                .client(client)
                .periodStart(request.getPeriodStart())
                .periodEnd(request.getPeriodEnd())
                .totalHours(totalHours)
                .totalAmount(totalAmount)
                .status(InvoiceStatus.PENDING)
                .build();

        Invoice saved = invoiceRepository.save(invoice);

        return toResponse(saved, sessions);
    }

    public InvoiceResponse findById(Long id){
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + id));

        List<ServiceSession> sessions = serviceSessionRepository.findByClientIdAndDateBetween(
                invoice.getClient().getId(),
                invoice.getPeriodStart(),
                invoice.getPeriodEnd()
        );

        return toResponse(invoice, sessions);
     }

     public List<InvoiceResponse> findByClient(Long clientId) {
        List<Invoice> invoices = invoiceRepository.findByClientId(clientId);
        return invoices.stream()
                .map(inv -> {
                    List<ServiceSession> sessions = serviceSessionRepository.findByClientIdAndDateBetween(
                            inv.getClient().getId(),
                            inv.getPeriodStart(),
                            inv.getPeriodEnd()
                    );
                    return toResponse(inv, sessions);
                })
                .toList();
     }

     public InvoiceResponse updateStatus(Long id, InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not find with id:  " + id));

        invoice.setStatus(status);
        Invoice saved = invoiceRepository.save(invoice);

        List<ServiceSession> sessions = serviceSessionRepository.findByClientIdAndDateBetween(
                saved.getClient().getId(),
                saved.getPeriodStart(),
                saved.getPeriodEnd()
        );

        return toResponse(saved, sessions);
     }

     private  InvoiceResponse toResponse(Invoice invoice, List<ServiceSession> sessions) {
        List<InvoiceItemResponse> items = sessions.stream()
                .map(s -> InvoiceItemResponse.builder()
                        .sessionId(s.getId())
                        .date(s.getDate())
                        .hoursWorked(s.getHoursWroked())
                        .description(s.getDescription())
                        .build()
                )
                .toList();

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .clientId(invoice.getClient().getId())
                .clientName(invoice.getClient().getName())
                .periodStart(invoice.getPeriodStart())
                .periodEnd(invoice.getPeriodEnd())
                .totalHours(invoice.getTotalHours())
                .totalAmount(invoice.getTotalAmount())
                .status(invoice.getStatus())
                .items(items)
                .build();
     }
}
