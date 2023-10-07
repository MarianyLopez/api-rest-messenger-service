package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ShipmentDTO;
import com.api.messengerservice.dtos.ShipmentMessageDTO;
import com.api.messengerservice.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<ShipmentMessageDTO> create(@RequestBody ShipmentDTO shipmentDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.create(shipmentDTO));
    }

    @PutMapping
    public ResponseEntity<ShipmentMessageDTO> updateDeliveryStatus(@RequestBody Map<String,Object> map) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(shipmentService.updateDeliveryStatus(map));
    }

    @GetMapping
    public ResponseEntity<ShipmentDTO> getShipmentInformation(@RequestBody Map<String,String> guideNumberJson) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentInformation(guideNumberJson));
    }
    @GetMapping("/list")
    public ResponseEntity<List<ShipmentDTO>> getShipmentByDeliveryStatus (@RequestBody Map<String,Object> map) {
        return  ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentByDeliveryStatus(map));
    }
}
