package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ClientDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> create (@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update (@PathVariable("id") Long id, @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.update(id,clientDTO).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById (@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(id));
    }
}
