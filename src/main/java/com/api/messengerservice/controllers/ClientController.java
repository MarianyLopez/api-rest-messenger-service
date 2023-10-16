package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ClientDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@SecurityRequirement(name = "BearerAuth")
public class ClientController {

    @Autowired
    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Endpoint to create a client",description = "In the body of the request it receives a Json with the client data needed to create it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created. Client successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request. The client already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @PostMapping
    public ResponseEntity<Client> create (@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientDTO));
    }

    @Operation(summary = "Endpoint to update a client",description = "Receives an ID by path and in the body of the request it receives a Json with the client data needed to update it")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. Client successfully updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The client does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @PutMapping("/{id}")
    public ResponseEntity<Client> update (@PathVariable("id") Long id, @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.update(id,clientDTO).get());
    }

    @Operation(summary = "Endpoint to delete a client",description = "Receives a Client ID by path to update it")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted. Client successfully removed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The client does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.delete(id));
    }

    @Operation(summary = "Endpoint to get a client by ID",description = "Receives a Client ID by path to get it")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. Client successfully received"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The client does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById (@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(id));
    }
}

