package com.api.messengerservice.services;

import com.api.messengerservice.entities.Shipment;
import com.api.messengerservice.repositories.PackageRepository;
import com.api.messengerservice.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private PackageRepository packageRepository;

    public ShipmentService(ShipmentRepository shipmentRepository,PackageRepository packageRepository) {
        this.shipmentRepository = shipmentRepository;
        this.packageRepository = packageRepository;
    }

    public Shipment create(Shipment shipment){
        return shipmentRepository.save(shipment);
    }

}
