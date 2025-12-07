package com.igorcavalcanti.invoice_service_api.service;

import com.igorcavalcanti.invoice_service_api.dtos.request.CreateClientRequest;
import com.igorcavalcanti.invoice_service_api.dtos.response.ClientResponse;
import com.igorcavalcanti.invoice_service_api.entity.Client;
import com.igorcavalcanti.invoice_service_api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientResponse create(CreateClientRequest request) {
        Client client = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .hourlyRate(request.getHourlyRate())
                .build();

        Client saved = clientRepository.save(client);
        System.out.println(saved.getId());
        return toResponse(saved);
    }

    public ClientResponse findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
        return toResponse(client);
    }

    public List<ClientResponse> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ClientResponse update(Long id, CreateClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        client.setHourlyRate(request.getHourlyRate());

        Client updated = clientRepository.save(client);
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    private ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .hourlyRate(client.getHourlyRate())
                .build();
    }
}
