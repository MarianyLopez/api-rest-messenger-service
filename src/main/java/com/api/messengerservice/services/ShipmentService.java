package com.api.messengerservice.services;

import com.api.messengerservice.dtos.ShipmentDTO;
import com.api.messengerservice.dtos.ShipmentMessageDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.entities.Package;
import com.api.messengerservice.entities.Shipment;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.InvalidEmployeeTypeException;
import com.api.messengerservice.repositories.ClientRepository;
import com.api.messengerservice.repositories.EmployeeRepository;
import com.api.messengerservice.repositories.ShipmentRepository;
import com.api.messengerservice.utils.DeliveryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public ShipmentService(ShipmentRepository shipmentRepository, ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.shipmentRepository = shipmentRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    public ShipmentMessageDTO create(ShipmentDTO shipmentDTO){
        Optional<Client> client = clientRepository.findById(shipmentDTO.getClientID());

        if (client.isPresent()) {
            Shipment shipmentToSave = new Shipment(client.get(),shipmentDTO.getOriginCity(),
                    shipmentDTO.getDestinationCity(), shipmentDTO.getDestinationAddress(),
                    shipmentDTO.getNamePersonReceives(), shipmentDTO.getPhonePersonReceives(),
                    new Package(shipmentDTO.getWeight(), shipmentDTO.getDeclaredValue()));
            shipmentRepository.save(shipmentToSave);
            return new ShipmentMessageDTO(shipmentToSave.getId(),shipmentToSave.getDeliveryStatus(),shipmentToSave.getShipmentPrice());
        }
        else {
            throw new InvalidCreateEntityException("Message: The client with ID " + shipmentDTO.getClientID() + " must be registered to be able to send a package");
        }
    }

    public ShipmentDTO getShipmentInformation(Map<String,String> stringMap) {
        Optional<Shipment> shipmentDB = shipmentRepository.findById(stringMap.get("guideNumber"));

        if (shipmentDB.isPresent()) {
            return createShipmentDTO(shipmentDB.get());
        }
        else
            throw new DoesNotExistEntityException("The shipment with guide number " + stringMap.get("guideNumber") + " does not exist");
    }

    private ShipmentDTO createShipmentDTO(Shipment shipment) {
        return new ShipmentDTO(shipment.getClient().getId(),shipment.getOriginCity(),shipment.getDestinationCity(),
                shipment.getDestinationAddress(),shipment.getNamePersonReceives(),shipment.getPhonePersonReceives(),
                shipment.getAPackage().getWeight(), shipment.getAPackage().getDeclaredValue(),shipment.getDeliveryStatus(),shipment.getShipmentPrice());
    }

    public ShipmentMessageDTO updateDeliveryStatus(Map<String,Object> map){
        Optional<Employee> employeeDB = employeeRepository.findById(((Number)map.get("employeeID")).longValue());
        String deliveryStatusToUpdate = map.get("deliveryStatus").toString();
        String guideNumber = map.get("guideNumber").toString();


        if (employeeDB.isPresent()) {
            String employeeType = employeeDB.get().getEmployeeType();
            if (employeeType.equalsIgnoreCase("Repartidor") || employeeType.equalsIgnoreCase("Coordinador")){
                Optional<Shipment> shipmentDB = shipmentRepository.findById(guideNumber);
                if (shipmentDB.isPresent()){
                    if (isValidUpdateDeliveryStatus(shipmentDB.get().getDeliveryStatus(),deliveryStatusToUpdate)){
                        shipmentDB.map(shipmentToUpdate -> {
                            shipmentToUpdate.setDeliveryStatus(deliveryStatusToUpdate);
                            return shipmentRepository.save(shipmentToUpdate);
                        });
                        return new ShipmentMessageDTO(guideNumber,deliveryStatusToUpdate,shipmentDB.get().getShipmentPrice());
                    }else
                        throw new RuntimeException("The status change does not comply with validations");
                }else
                    throw new DoesNotExistEntityException("The shipment with guide number " + guideNumber + " does not exist");
            }else
                throw new InvalidEmployeeTypeException("The employee type " + employeeDB.get().getEmployeeType() + " is not authorized");
        }else
            throw new DoesNotExistEntityException("The employee with ID " + map.get("employeeID").toString() + " does not exist");
    }

    public List<ShipmentDTO> getShipmentByDeliveryStatus(Map<String,Object> map) {
        Optional<Employee> employeeDB = employeeRepository.findById(((Number)map.get("employeeID")).longValue());
        if (employeeDB.isPresent()){
            if (isCorrectDeliveryStatus(map.get("deliveryStatus").toString())) {
                List<Shipment> shipments = shipmentRepository.findByDeliveryStatus(map.get("deliveryStatus").toString());
                List<ShipmentDTO> shipmentDTOS = new ArrayList<>();
                for (Shipment shipment: shipments) {
                    ShipmentDTO shipmentDTO = createShipmentDTO(shipment);
                    shipmentDTOS.add(shipmentDTO);
                }
                return shipmentDTOS;
            }else
                throw new RuntimeException("The Delivery Status is not valid");
        }else
            throw new DoesNotExistEntityException("The employee with ID " + map.get("employeeID").toString() + " does not exist");
    }

    private boolean isValidUpdateDeliveryStatus(String status, String newStatus){
        return (status.equalsIgnoreCase("Recibido") && newStatus.equalsIgnoreCase("En ruta")) || (status.equalsIgnoreCase("En ruta") && newStatus.equalsIgnoreCase("Entregado"));
    }

    private boolean isCorrectDeliveryStatus (String delivery) {
        return (delivery.equalsIgnoreCase(DeliveryStatus.RECIBED.getNameSpanish()) || delivery.equalsIgnoreCase(DeliveryStatus.ONROUTE.getNameSpanish()) || delivery.equalsIgnoreCase(DeliveryStatus.DELIVERED.getNameSpanish()));
    }
}
