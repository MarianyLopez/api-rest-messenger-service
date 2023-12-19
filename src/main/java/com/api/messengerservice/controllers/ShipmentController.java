package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ShipmentDTO;
import com.api.messengerservice.dtos.ShipmentMessageDTO;
import com.api.messengerservice.dtos.ShipmentResponseDTO;
import com.api.messengerservice.services.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shipment")
@SecurityRequirement(name = "BearerAuth")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Operation(summary = "Endpoint to create a shipment", description = "In the body of the request it receives a Json with the shipment data needed to create it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created. Shipment successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request. The client does not exist"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @PostMapping
    public ResponseEntity<ShipmentMessageDTO> create(@RequestBody ShipmentDTO shipmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.create(shipmentDTO));
    }

    @Operation(summary = "Endpoint to update a delivery status", description = "The body of the request it receives a Json with the shipment data needed to update delivery status. " +
            "Remember that only an employee of type 'Coordinador' or 'Repartidor' can change the status of a shipment.")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted. Delivery Status successfully updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The employee or shipment do not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null or the status change does not comply with validations or the employee type is not authorized")})
    @PutMapping
    public ResponseEntity<ShipmentMessageDTO> updateDeliveryStatus(@RequestBody @Schema(
            type = "object",
            properties = {
                    @StringToClassMapItem(key = "guideNumber",value = String.class),
                    @StringToClassMapItem(key = "deliveryStatus",value = String.class),
                    @StringToClassMapItem(key = "employeeID", value = Integer.class)
            }
    ) Map<String, Object> map) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(shipmentService.updateDeliveryStatus(map));
    }

    @Operation(summary = "Endpoint to get a shipment information by guide number", description = "Receives a Json with a guide number in the body of the request to get shipment information.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. Shipment Information successfully received"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The employee does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null or The Delivery Status is not valid")})
    @GetMapping
    public ResponseEntity<ShipmentResponseDTO> getShipmentInformation(@RequestBody @Schema(
            type = "object",
            properties = {
                    @StringToClassMapItem(key = "guideNumber", value = String.class)
            }) Map<String, String> guideNumberJson) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentInformation(guideNumberJson));
    }

    @Operation(summary = "Endpoint to get a list of shipments by shipment status", description = "Receives a Json with the status of a shipment and the employee id in the body of the request to get the list of shipments currently in that status.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. Shipment Information successfully received"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The employee does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null or The Delivery Status is not valid")})
    @GetMapping("/list")
    public ResponseEntity<List<ShipmentResponseDTO>> getShipmentByDeliveryStatus(@RequestBody @Schema(
            type = "object",
            properties = {
                    @StringToClassMapItem(key = "deliveryStatus", value = String.class),
                    @StringToClassMapItem(key = "employeeID", value = Integer.class)
            }
    ) Map<String, Object> map) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentByDeliveryStatus(map));
    }
}
