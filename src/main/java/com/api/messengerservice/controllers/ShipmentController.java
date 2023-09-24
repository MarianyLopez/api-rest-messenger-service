package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ShipmentDTO;
import com.api.messengerservice.repositories.ClientRepository;
import com.api.messengerservice.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;
    private final ClientRepository clientRepository;

    public ShipmentController(ShipmentService shipmentService,
                              ClientRepository clientRepository) {
        this.shipmentService = shipmentService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ShipmentDTO shipmentDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.create(shipmentDTO));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getShipmentInformation(@RequestBody Map<String,String> guideNumberJson) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentInformation(guideNumberJson));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateDeliveryStatus (@RequestBody Map<String,Object> map) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(shipmentService.updateDeliveryStatus(map));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @GetMapping("/list")
    public ResponseEntity<Object> getShipmentByDeliveryStatus (@RequestBody Map<String,Object> map) {
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentByDeliveryStatus(map));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
