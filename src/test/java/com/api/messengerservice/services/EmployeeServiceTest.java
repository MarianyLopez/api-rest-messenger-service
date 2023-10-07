package com.api.messengerservice.services;

import com.api.messengerservice.dtos.EmployeeDTO;
import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.InvalidEmployeeTypeException;
import com.api.messengerservice.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;

    private Employee employee;
    @BeforeEach
    public void setUp(){
        employeeDTO = new EmployeeDTO(1234L,"Dahier","Rivera","3005789609","dahier@gmail.com","Calle 45","Medellín",2,"B+","Coordinador");
        employee = new Employee(employeeDTO.getId(),employeeDTO.getName(),employeeDTO.getLastName(),employeeDTO.getPhoneNumber(),employeeDTO.getEmail(),employeeDTO.getAddress(),employeeDTO.getCity(),employeeDTO.getSeniorityCompany(),employeeDTO.getBloodType(),employeeDTO.getEmployeeType());
        employeeService =  new EmployeeService(employeeRepository);
    }

    @Test
    void createEmployeeSuccessfully() {

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Employee employeeResult = employeeService.create(employeeDTO);

        Assertions.assertEquals(employee,employeeResult);
    }

    @Test
    void employeeCreationFailsBecauseEmployeeAlreadyExists(){

        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Assertions.assertThrows(InvalidCreateEntityException.class,()->{
           employeeService.create(employeeDTO);
        });
    }

    @Test
    void employeeCreationFailsBecauseEmployeeTypeIsInvalid(){

        EmployeeDTO employeeDTO = new EmployeeDTO(1234L,"Dahier","Rivera","3005789609","dahier@gmail.com","Calle 45","Medellín",2,"B+","Cocinero");

        Assertions.assertThrows(InvalidEmployeeTypeException.class,()->{
           employeeService.create(employeeDTO);
        });
    }

    @Test
    void updateEmployeeSuccessfully() {

        Mockito.when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.of(employee));

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Optional<Employee> employeeResult = employeeService.update(employeeDTO.getId(), employeeDTO);

        Assertions.assertEquals(employee,employeeResult.get());

    }

    @Test
    void employeeUpdateFailsBecauseEmployeeDoesNotExists(){

        Mockito.when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class,()->{
            employeeService.update(employeeDTO.getId(),employeeDTO);
        });
    }

    @Test
    void deleteEmployeeByIdSuccessfully() {

        Mockito.when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.of(employee));

        Assertions.assertTrue(("message : The employee with ID " + employeeDTO.getId() + " was successfully removed").equalsIgnoreCase(employeeService.delete(employeeDTO.getId())));
    }

    @Test
    void employeeDeleteFailsBecauseEmployeeDoesExists(){

        Mockito.when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class, ()->{
            employeeService.delete(employeeDTO.getId());
        });
    }

    @Test
    void getEmployeeByIdSuccessfully() {

        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Assertions.assertEquals(employee,(employeeService.getEmployeeById(employee.getId())));

    }

    @Test
    void getEmployeeByIdFailsBecauseEmployeeDoesNotExists(){

        Mockito.when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(DoesNotExistEntityException.class,()->{
            employeeService.getEmployeeById(employeeDTO.getId());
        });
    }
}