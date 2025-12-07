package com.igorcavalcanti.invoice_service_api.controller;


import com.igorcavalcanti.invoice_service_api.dtos.request.GenerateInvoiceRequest;
import com.igorcavalcanti.invoice_service_api.dtos.response.InvoiceResponse;
import com.igorcavalcanti.invoice_service_api.entity.InvoiceStatus;
import com.igorcavalcanti.invoice_service_api.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<InvoiceResponse> generateInvoice(
            @Valid @RequestBody GenerateInvoiceRequest request
    ) {
        InvoiceResponse response = invoiceService.generateInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        InvoiceResponse response = invoiceService.findById(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getInvoiceByClient(
            @RequestParam Long clientId
    ) {
        List<InvoiceResponse> responses = invoiceService.findByClient(clientId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InvoiceResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam InvoiceStatus status

            ) {
        InvoiceResponse response = invoiceService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
