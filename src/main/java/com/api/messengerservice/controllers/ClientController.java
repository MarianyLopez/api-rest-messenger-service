package com.api.messengerservice.controllers;

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
    public ResponseEntity<Object> create (@RequestBody Client client) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.create(client));
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update (@PathVariable("id") Long id, @RequestBody Client client) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.update(id,client));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable("id") Long id) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.delete(id));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientById (@PathVariable("id") Long id) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(id));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
