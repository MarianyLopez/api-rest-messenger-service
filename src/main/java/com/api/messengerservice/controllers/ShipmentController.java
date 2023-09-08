package com.api.messengerservice.controllers;

import com.api.messengerservice.entities.Shipment;
import com.api.messengerservice.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public Shipment create(@RequestBody Shipment shipment){
        return shipmentService.create(shipment);
    }

}
