package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ClientDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.services.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ClientController clientController;

    @BeforeEach
    void setUp(){
        clientController = new ClientController(clientService);
    }
    @Test
    void statusCreatedOnSuccessfulClientCreation() {
        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");
        Client client = new Client(clientDTO.getId(),clientDTO.getName(),clientDTO.getLastName(), clientDTO.getPhoneNumber(), clientDTO.getEmail(), clientDTO.getAddress(), clientDTO.getCity());

        Mockito.when(clientService.create(Mockito.any(ClientDTO.class))).thenReturn(client);

        ResponseEntity<Client> responseEntity = clientController.create(clientDTO);

        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }
    @Test
    void theClientServiceGenerateTheExceptions() {
        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientService.create(Mockito.any(ClientDTO.class))).thenThrow(new InvalidCreateEntityException("Error: The client already exists"));


        Assertions.assertThrows(InvalidCreateEntityException.class,()->{
            ResponseEntity<Client> responseEntity = clientController.create(clientDTO);
        });
    }

    @Test
    void statusOkOnSuccessfulClientUpdate() {
        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");
        Client client = new Client(clientDTO.getId(),clientDTO.getName(),clientDTO.getLastName(), clientDTO.getPhoneNumber(), clientDTO.getEmail(), clientDTO.getAddress(), clientDTO.getCity());

        Mockito.when(clientService.update(clientDTO.getId(),clientDTO)).thenReturn(Optional.of(client));

        ResponseEntity<Client> responseEntity = clientController.update(clientDTO.getId(),clientDTO);

        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void statusAcceptedOnSuccessfulClientDelete() {
        String message = "message : The client with ID " + 1234L + " was successfully removed";

        Mockito.when(clientService.delete(1234L)).thenReturn(message);

        ResponseEntity<String> response = clientController.delete(1234L);

        Assertions.assertEquals(message,response.getBody());

        Assertions.assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
    }

    @Test
    void statusOkOnGetClientByIdBecauseClientExists() {
        Client client = new Client(1234L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientService.getClientById(1234L)).thenReturn(client);

        ResponseEntity<Client> responseEntity = clientController.getClientById(client.getId());

        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}