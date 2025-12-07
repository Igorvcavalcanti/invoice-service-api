package com.igorcavalcanti.invoice_service_api.controller;

import com.igorcavalcanti.invoice_service_api.dtos.request.CreateServiceSessionRequest;
import com.igorcavalcanti.invoice_service_api.dtos.response.ServiceSessionResponse;
import com.igorcavalcanti.invoice_service_api.service.ServiceSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class ServiceSessionController {

    private final ServiceSessionService serviceSessionService;

    @PostMapping
    public ResponseEntity<ServiceSessionResponse> createSession(
            @Valid @RequestBody CreateServiceSessionRequest request
    ){
        ServiceSessionResponse response = serviceSessionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServiceSessionResponse>> getSessionByClient(
            @RequestParam Long clientId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<ServiceSessionResponse> responses;

        if (startDate != null && endDate != null) {
            responses = serviceSessionService.findByClientAndPeriod(clientId, startDate, endDate);
        } else {
            responses = serviceSessionService.findByClient(clientId);
        }

        return ResponseEntity.ok(responses);

    }
}
