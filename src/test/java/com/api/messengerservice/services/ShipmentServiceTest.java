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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        shipmentService = new ShipmentService(shipmentRepository,clientRepository,employeeRepository);
    }
    @Test
    void createShipmentSuccessfully() {
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");
        ShipmentDTO shipmentDTO = new ShipmentDTO(908L,"Cali","Cartagena", "Calle Colombia","Mariany","302222",6,50.000);
        Shipment shipment = new Shipment(client,shipmentDTO.getOriginCity(),shipmentDTO.getDestinationCity(),shipmentDTO.getDestinationAddress(),shipmentDTO.getNamePersonReceives(),shipmentDTO.getPhonePersonReceives(), new Package(shipmentDTO.getWeight(),shipmentDTO.getDeclaredValue()));
        ShipmentMessageDTO shipmentMessageDTO = new ShipmentMessageDTO(shipment.getId(),shipment.getDeliveryStatus());

        Mockito.when(clientRepository.findById(shipmentDTO.getClientID())).thenReturn(Optional.of(client));
        Mockito.when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(shipment);

        Assertions.assertTrue(!shipmentService.create(shipmentDTO).toString().isEmpty());
    }

    @Test
    void createShipmentFailsBecauseClientIdIsNotPresent() {
        ShipmentDTO shipmentDTO = new ShipmentDTO(908L,"Cali","Cartagena", "Calle Colombia","Mariany","302222",6,50.000);

        Mockito.when(clientRepository.findById(shipmentDTO.getClientID())).thenReturn(Optional.empty());

        Assertions.assertThrows(InvalidCreateEntityException.class,()-> {
            shipmentService.create(shipmentDTO);
        });
    }

    @Test
    void getShipmentInformationSuccessfully() {
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");
        ShipmentDTO shipmentDTO = new ShipmentDTO(908L,"Cali","Cartagena", "Calle Colombia","Mariany","302222",6,50.000);
        Shipment shipment = new Shipment(client,shipmentDTO.getOriginCity(),shipmentDTO.getDestinationCity(),shipmentDTO.getDestinationAddress(),shipmentDTO.getNamePersonReceives(),shipmentDTO.getPhonePersonReceives(), new Package(shipmentDTO.getWeight(),shipmentDTO.getDeclaredValue()));
        shipment.setId("GN12345");
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("guideNumber", "GN12345");

        Mockito.when(shipmentRepository.findById(shipment.getId())).thenReturn(Optional.of(shipment));

        ShipmentDTO shipmentDTOResult = shipmentService.getShipmentInformation(stringMap);

        Assertions.assertNotNull(shipmentDTOResult);

    }

    @Test
    void getShipmentInformationFailsBecauseShipmentGuideIsNotPresent() {
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("guideNumber", "AS3465$#");

        Mockito.when(shipmentRepository.findById(stringMap.get("guideNumber"))).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class, ()->{
            shipmentService.getShipmentInformation(stringMap);
        });
    }
    @Test
    void updateDeliveryStatusSuccessfully() {
        Employee employee = new Employee(1234L,"Dilan","Quintero","4323","dilan@gmail.com","Calle 67","Medayork",5,"B+","Repartidor");
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");
        Shipment shipment = new Shipment(client,"Cali","Cartagena", "Calle Colombia","Mariany","302222",new Package(6,50.000));
        shipment.setId("GUIDENUMBER123");

        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "En ruta");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.of(employee));
        Mockito.when(shipmentRepository.findById(shipment.getId())).thenReturn(Optional.of(shipment));
        Mockito.when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(shipment);

        ShipmentMessageDTO shipmentMessageDTO = shipmentService.updateDeliveryStatus(map);

        Assertions.assertNotNull(shipmentMessageDTO);
        Assertions.assertEquals("En ruta",shipmentMessageDTO.getDeliveryStatus());
    }

    @Test
    void updateDeliveryStatusFailsBecauseEmployeeDoesNotExists() {

        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "En ruta");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class,()->{
            shipmentService.updateDeliveryStatus(map);
        });

    }

    @Test
    void updateDeliveryStatusFailsBecauseEmployeeTypeIsNotValid() {

        Employee employee = new Employee(1234L,"Dilan","Quintero","4323","dilan@gmail.com","Calle 67","Medayork",5,"B+","Cocinero");

        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "En ruta");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.of(employee));

        Assertions.assertThrows(InvalidEmployeeTypeException.class,()->{
            shipmentService.updateDeliveryStatus(map);
        });
    }

    @Test
    void updateDeliveryStatusFailsBecauseShipmentDoesNotExists() {
        Employee employee = new Employee(1234L,"Dilan","Quintero","4323","dilan@gmail.com","Calle 67","Medayork",5,"B+","Repartidor");
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");
        Shipment shipment = new Shipment(client,"Cali","Cartagena", "Calle Colombia","Mariany","302222",new Package(6,50.000));
        shipment.setId("GUIDENUMBER123");

        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "En ruta");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.of(employee));
        Mockito.when(shipmentRepository.findById(shipment.getId())).thenReturn(Optional.empty());

       Assertions.assertThrows(DoesNotExistEntityException.class,()->{
           shipmentService.updateDeliveryStatus(map);
       });
    }

    @Test
    void updateDeliveryStatusFailsBecauseTheChangeDoesNotComplyWithValidations(){
        Employee employee = new Employee(1234L,"Dilan","Quintero","4323","dilan@gmail.com","Calle 67","Medayork",5,"B+","Repartidor");
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");
        Shipment shipment = new Shipment(client,"Cali","Cartagena", "Calle Colombia","Mariany","302222",new Package(6,50.000));
        shipment.setId("GUIDENUMBER123");

        Map<String,Object> map = new HashMap<>();
        map.put("guideNumber", "GUIDENUMBER123");
        map.put("deliveryStatus", "Recibido");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.of(employee));
        Mockito.when(shipmentRepository.findById(shipment.getId())).thenReturn(Optional.of(shipment));

        Assertions.assertThrows(RuntimeException.class,()->{
            shipmentService.updateDeliveryStatus(map);
        });

    }

    @Test
    void getShipmentByDeliveryStatusSuccessfully() {
        Employee employee = new Employee(1234L,"Dilan","Quintero","4323","dilan@gmail.com","Calle 67","Medayork",5,"B+","Repartidor");
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");
        List<Shipment> shipments = new ArrayList<>();
        shipments.add(new Shipment(client,"Cali","Cartagena", "Calle Colombia","Mariany","302222",new Package(6,50.000)));
        shipments.add(new Shipment(client,"Cali","Medellín", "Calle Colombia","Mariany","302222",new Package(2,50.000)));

        Map<String,Object> map = new HashMap<>();
        map.put("deliveryStatus", "Recibido");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.of(employee));
        Mockito.when(shipmentRepository.findByDeliveryStatus(map.get("deliveryStatus").toString())).thenReturn(shipments);

        Assertions.assertNotNull(shipmentService.getShipmentByDeliveryStatus(map));
    }

    @Test
    void getShipmentByDeliveryStatusFailsBecauseEmployeeDoesNotExists(){
        Client client = new Client(908L,"Juan","Lopez","302987","juan@gmail.com","Carreara 50","Medayor");

        Map<String,Object> map = new HashMap<>();
        map.put("deliveryStatus", "Recibido");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class,()->{
            shipmentService.getShipmentByDeliveryStatus(map);
        });
    }

    @Test
    void getShipmentByDeliveryStatusFailsBecauseTheDeliveryStatusIsNotValid(){
        Employee employee = new Employee(1234L,"Dilan","Quintero","4323","dilan@gmail.com","Calle 67","Medayork",5,"B+","Repartidor");

        Map<String,Object> map = new HashMap<>();
        map.put("deliveryStatus", "Transportado");
        map.put("employeeID", 1234L);

        Mockito.when(employeeRepository.findById(1234L)).thenReturn(Optional.of(employee));

        Assertions.assertThrows(RuntimeException.class,()->{
            shipmentService.getShipmentByDeliveryStatus(map);
        });
    }

}