package com.api.messengerservice.services;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.IsNotExistEntityException;
import com.api.messengerservice.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create (Client client) {
        if (clientRepository.findById(client.getId()).isPresent())
            throw new InvalidCreateEntityException("Error: The client already exists");
        else
            return clientRepository.save(client);
    }

    public Optional<Client> update (Long id, Client client) {

        Optional<Client> clientDb = clientRepository.findById(id);

        if (clientDb.isEmpty())
            throw new IsNotExistEntityException ("Error : The employee with ID " + id + " does not exist");
        else {
            return clientDb
                    .map(clientSave -> {
                        clientSave.setName(client.getName());
                        clientSave.setLastName(client.getLastName());
                        clientSave.setEmail(client.getEmail());
                        clientSave.setPhoneNumber(client.getPhoneNumber());
                        clientSave.setAddress(client.getAddress());
                        clientSave.setCity(client.getCity());

                        return clientRepository.save(clientSave);
                    });
        }
    }

    public String delete (Long id) {
        Optional<Client> clientDb = clientRepository.findById(id);

        if (clientDb.isPresent()) {
            clientRepository.deleteById(id);
            return "message : The client with ID " + id + " was successfully removed";
        }else
            return "message: The client with ID " + id + " does not exist";
    }

    public Optional<Client> getClientById (Long id) {

        Optional<Client> clientDb = clientRepository.findById(id);

        if (clientDb.isEmpty())
            throw new IsNotExistEntityException("Error : The client with ID " + id + " does not exist");
        else
            return clientDb;


    }
}
