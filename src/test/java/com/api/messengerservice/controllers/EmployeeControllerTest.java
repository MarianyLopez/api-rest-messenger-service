package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.EmployeeDTO;
import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;
    @Mock
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController(employeeService);
    }
    @Test
    void statusCreatedOnSuccessfulEmployeeCreation() {

        EmployeeDTO employeeDTO = new EmployeeDTO(1209L,"Arturo","Calle", "309472","arturito@gmail.com","Calle Aturo 56","Madrid", 20,"A+","Coordinador");
        Employee employee = new Employee(employeeDTO.getId(),employeeDTO.getName(),employeeDTO.getLastName(),employeeDTO.getPhoneNumber(),employeeDTO.getEmail(),employeeDTO.getAddress(),employeeDTO.getCity(),employeeDTO.getSeniorityCompany(),employeeDTO.getBloodType(),employeeDTO.getEmployeeType());

        Mockito.when(employeeService.create(Mockito.any(EmployeeDTO.class))).thenReturn(employee);

        ResponseEntity<Employee> responseEntity = employeeController.create(employeeDTO);

        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }

    @Test
    void statusOkOnSuccessfulEmployeeUpdate() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1209L,"Arturo","Calle", "309472","arturito@gmail.com","Calle Aturo 56","Madrid", 20,"A+","Coordinador");
        Employee employee = new Employee(employeeDTO.getId(),employeeDTO.getName(),employeeDTO.getLastName(),employeeDTO.getPhoneNumber(),employeeDTO.getEmail(),employeeDTO.getAddress(),employeeDTO.getCity(),employeeDTO.getSeniorityCompany(),employeeDTO.getBloodType(),employeeDTO.getEmployeeType());

        Mockito.when(employeeService.update(employeeDTO.getId(),employeeDTO)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> responseEntity = employeeController.update(employeeDTO.getId(), employeeDTO);

        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void statusAcceptedOnSuccessfulEmployeeDelete() {

        String message = "message : The Employee with ID " + 1234L + " was successfully removed";

        Mockito.when(employeeService.delete(1234L)).thenReturn(message);

        ResponseEntity<String> responseEntity = employeeController.delete(1234L);

        Assertions.assertEquals(message,responseEntity.getBody());

        Assertions.assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
    }

    @Test
    void statusOkOnGetEmployeeByIdBecauseEmployeeExists() {
        Employee employee = new Employee(1209L,"Arturo","Calle", "309472","arturito@gmail.com","Calle Aturo 56","Madrid", 20,"A+","Coordinador");

        Mockito.when(employeeService.getEmployeeById(1209L)).thenReturn(employee);

        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(employee.getId());

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}