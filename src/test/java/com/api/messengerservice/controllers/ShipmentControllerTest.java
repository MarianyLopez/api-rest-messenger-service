package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ShipmentDTO;
import com.api.messengerservice.dtos.ShipmentMessageDTO;
import com.api.messengerservice.dtos.ShipmentResponseDTO;
import com.api.messengerservice.entities.Client;
import com.api.messengerservice.services.ShipmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;
    @Mock
    private ShipmentController shipmentController;

    @BeforeEach
    void setUp() {
        shipmentController = new ShipmentController(shipmentService);
    }

    @Test
    void statusCreatedOnSuccessfulShipmentCreation() {
        Client client = new Client(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");
        ShipmentDTO shipmentDTO = new ShipmentDTO(client.getId(),"Sidney","Berlin","Street 23","Carlosky","234321",45,90.000);
        ShipmentMessageDTO shipmentMessageDTO = new ShipmentMessageDTO("1234Cas","Recibido",30000);

        Mockito.when(shipmentService.create(Mockito.any(ShipmentDTO.class))).thenReturn(shipmentMessageDTO);

        ResponseEntity<ShipmentMessageDTO> responseEntity = shipmentController.create(shipmentDTO);

        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }

    @Test
    void statusOkOnSuccessfulDeliveryStatusUpdate() {
        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "En ruta");
        map.put("employeeID", 1234L);
        ShipmentMessageDTO shipmentMessageDTO = new ShipmentMessageDTO("GUIDENUMBER123","En ruta",30000);

        Mockito.when(shipmentService.updateDeliveryStatus(map)).thenReturn(shipmentMessageDTO);

        ResponseEntity<ShipmentMessageDTO> responseEntity = shipmentController.updateDeliveryStatus(map);

        Assertions.assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
    }

    @Test
    void statusOkOnGetShipmentInformation() {
        Map<String,String> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        ShipmentResponseDTO shipmentDTO = new ShipmentResponseDTO(1246L,"Sidney","Berlin","Street 23","Carlosky","234321",45,90.000,"Recibido",30000);

        Mockito.when(shipmentService.getShipmentInformation(map)).thenReturn(shipmentDTO);

        ResponseEntity<ShipmentResponseDTO> responseEntity = shipmentController.getShipmentInformation(map);

        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void statusOkOnGetShipmentByDeliveryStatus() {
        Map<String,Object> map = new HashMap<>();
        map.put("deliveryStatus", "Recibido");
        map.put("employeeID", 1234L);
        List<ShipmentResponseDTO> shipments = new ArrayList<>();
        shipments.add(new ShipmentResponseDTO(123L,"Cali","Cartagena", "Calle Colombia","Mariany","302222",6,50.000,"Recibido",30000));
        shipments.add(new ShipmentResponseDTO(321L,"Cali","Medellín", "Calle Colombia","Mariany","302222",2,50.000,"Recibido",30000));

        Mockito.when(shipmentService.getShipmentByDeliveryStatus(map)).thenReturn(shipments);

        ResponseEntity<List<ShipmentResponseDTO>> responseEntity = shipmentController.getShipmentByDeliveryStatus(map);
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

}