package com.api.messengerservice.services;
import com.api.messengerservice.dtos.ClientDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.entities.Shipment;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.repositories.ClientRepository;
import com.api.messengerservice.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private ShipmentRepository shipmentRepository;

    public ClientService(ClientRepository clientRepository,ShipmentRepository shipmentRepository) {
        this.clientRepository = clientRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public Client create (ClientDTO clientDTO) {
        if (clientRepository.findById(clientDTO.getId()).isPresent())
            throw new InvalidCreateEntityException("Error: The client already exists");
        else
            return clientRepository.save(createClient(clientDTO));
    }

    public Optional<Client> update (Long id, ClientDTO clientDTO) {
        Optional<Client> clientDb = clientRepository.findById(id);

        if (clientDb.isEmpty())
            throw new DoesNotExistEntityException("Error : The employee with ID " + id + " does not exist");
        else {
            return clientDb
                    .map(clientSave -> {
                        clientSave.setName(clientDTO.getName());
                        clientSave.setLastName(clientDTO.getLastName());
                        clientSave.setEmail(clientDTO.getEmail());
                        clientSave.setPhoneNumber(clientDTO.getPhoneNumber());
                        clientSave.setAddress(clientDTO.getAddress());
                        clientSave.setCity(clientDTO.getCity());

                        return clientRepository.save(clientSave);
                    });
        }
    }

    public String delete (Long id) {
        Optional<Client> clientDb = clientRepository.findById(id);

        if (clientDb.isPresent()) {
            List<Shipment> shipments = shipmentRepository.findByClientId(id);
            shipments.forEach(shipment->shipmentRepository.delete(shipment));
            clientRepository.deleteById(id);
            return "message : The client with ID " + id + " was successfully removed";
        }else
            return "message: The client with ID " + id + " does not exist";
    }

    public Optional<Client> getClientById (Long id) {
        Optional<Client> clientDb = clientRepository.findById(id);

        if (clientDb.isEmpty())
            throw new DoesNotExistEntityException("Error : The client with ID " + id + " does not exist");
        else
            return clientDb;


    }

    private Client createClient(ClientDTO clientDTO){
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getLastName(),
                clientDTO.getPhoneNumber(), clientDTO.getEmail(), clientDTO.getAddress(),
                clientDTO.getCity());
    }
}
