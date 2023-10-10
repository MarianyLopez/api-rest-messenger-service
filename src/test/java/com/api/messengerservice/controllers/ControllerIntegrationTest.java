package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.ClientDTO;
import com.api.messengerservice.dtos.EmployeeDTO;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.InvalidEmployeeTypeException;
import com.api.messengerservice.services.ClientService;
import com.api.messengerservice.services.EmployeeService;
import com.api.messengerservice.services.ShipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private ShipmentService shipmentService;

    @Test
    public void statusBadRequestOnClientCreationBecauseClientAlreadyExist() throws Exception {

        String token = generateSimulatedJwtToken();

        ClientDTO clientDTO = new ClientDTO(1001L,"Dilan","Quintero","3006782537","dilan@gmail.com","Carrera 35","Medellín");


        Mockito.when(clientService.create(Mockito.any(ClientDTO.class))).thenThrow(new InvalidCreateEntityException("Error : The client already exists"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/client")
                        .header("Authorization","Bearer "+token)
                        .content(new ObjectMapper().writeValueAsString(clientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void statusConflictOnClientCreationBecauseClientArgumentsAreNull() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        String token = generateSimulatedJwtToken();

        Mockito.when(clientService.create(Mockito.any(ClientDTO.class))).thenThrow(new IllegalArgumentException("Error : Client arguments cannot be null"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/client")
                        .header("Authorization","Bearer "+token)
                        .content(new ObjectMapper().writeValueAsString(clientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict());
    }

    @Test
    void statusOkOnGetClientByIdBecauseClientExists() throws Exception {
        Long idClient = 123L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(idClient);
        String token = generateSimulatedJwtToken();

        Mockito.when(clientService.getClientById(idClient)).thenThrow(new DoesNotExistEntityException("Error : The client with ID " + idClient + " does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/client/{id}",idClient)
                .header("Authorization","Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void statusConflictOnEmployeeCreationBecauseEmployeeTypeIsInvalid() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(123L,"Arley","Aestetic","32445","josueestetik@gmail.com","Calle 13","Caldas",4,"A-","Calvo");

        String token = generateSimulatedJwtToken();

        Mockito.when(employeeService.create(Mockito.any(EmployeeDTO.class))).thenThrow(new InvalidEmployeeTypeException("Error : The employee has not been created because the employee type is not valid"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                        .header("Authorization","Bearer " + token)
                        .content(new ObjectMapper().writeValueAsString(employeeDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict());
    }

    @Test
    void statusConflictOnShimpmentDeliveryStatusUpdate() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "BlueLabel");
        map.put("employeeID", 1234L);

        String token = generateSimulatedJwtToken();

        Mockito.when(shipmentService.updateDeliveryStatus(Mockito.anyMap())).thenThrow(new RuntimeException("The status change does not comply with validations"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shipment")
                        .header("Authorization","Bearer " + token)
                        .content(new ObjectMapper().writeValueAsString(map))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict());
    }

    private String generateSimulatedJwtToken() {
        return Jwts.builder()
                .setSubject("admin@gmail.com")
                .signWith(Keys.hmacShaKeyFor("4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud".getBytes()))
                .compact();
    }
}


