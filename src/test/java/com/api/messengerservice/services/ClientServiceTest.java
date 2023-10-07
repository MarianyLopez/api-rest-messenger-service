package com.api.messengerservice.services;

import com.api.messengerservice.dtos.ClientDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.repositories.ClientRepository;
import com.api.messengerservice.repositories.ShipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ClientService clientService;
    @BeforeEach
    public void setUp(){
        clientService =  new ClientService(clientRepository,shipmentRepository);
    }
    @Test
    void createClientSuccessfully() {

        ClientDTO clientDTO = new ClientDTO(1234L,"Mariany","López","3014215789","mariany@gmail.com","Calle 57","Medellín");
        Client client = new Client(clientDTO.getId(),clientDTO.getName(),clientDTO.getLastName(),clientDTO.getPhoneNumber(),clientDTO.getEmail(), clientDTO.getAddress(), clientDTO.getCity());

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client clientResult = clientService.create(clientDTO);

        Assertions.assertEquals(client,clientResult);
    }

    @Test
    void clientCreationFailsBecauseClientAlreadyExists(){

        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");
        Client client = new Client(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientRepository.findById(clientDTO.getId())).thenReturn(Optional.of(client));

        Assertions.assertThrows(InvalidCreateEntityException.class,()->{
            clientService.create(clientDTO);
        });

    }

    @Test
    void updateClientSuccessfully() {
        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");
        Client client = new Client(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientRepository.findById(clientDTO.getId())).thenReturn(Optional.of(client));

        Mockito.when(clientRepository.save(client)).thenReturn(client);

        Optional<Client> clientResult = clientService.update(clientDTO.getId(),clientDTO);

        Assertions.assertEquals(client,clientResult.get());

    }

    @Test
    void clientUpdateFailsBecauseClientDoesNotExist(){
        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");
        Client client = new Client(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientRepository.findById(clientDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class,()->{
            clientService.update(clientDTO.getId(),clientDTO);
        });

    }


    @Test
    void deleteClientByIdSuccessfully() {
        Client client = new Client(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Assertions.assertTrue(("message : The client with ID " + client.getId() + " was successfully removed").equalsIgnoreCase(clientService.delete(client.getId())));

    }

    @Test
    void clientDeleteFailsBecauseClientDoesNotExist(){

        Long id = 1001L;

        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class, ()->{
            clientService.delete(id);
        });

    }


    @Test
    void getClientByIdSuccessfully() {

        Client client = new Client(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");

        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Assertions.assertEquals(client,(clientService.getClientById(client.getId())));

    }
    @Test
    void getClientByIdFailsBecauseClientDoesNotExist(){
        Long id = 1001L;

        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class,()->{
            clientService.getClientById(id);
        });
    }
}