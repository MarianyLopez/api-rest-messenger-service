package com.api.messengerservice.services;

import com.api.messengerservice.dtos.EmployeeDTO;
import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.utils.EmployeeType;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.InvalidEmployeeTypeException;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(EmployeeDTO employeeDTO) throws InvalidEmployeeTypeException {

            if (employeeRepository.findById(employeeDTO.getId()).isPresent())
                throw new InvalidCreateEntityException("Error: The employee already exists");
            else if (!isCorrectEmployeeType(employeeDTO))
                throw new InvalidEmployeeTypeException("Error : The employee has not been created because the employee type is not valid");
            return employeeRepository.save(createEmployee(employeeDTO));

    }

    public Optional<Employee> update(Long id, EmployeeDTO employeeDTO){

        Optional<Employee> employeeDb = employeeRepository.findById(id);

        if (employeeDb.isEmpty()){
            throw new DoesNotExistEntityException("Error : The employee with ID " + id + " does not exist");
        }else {
           return employeeDb
                    .map(employeeSaved -> {
                        employeeSaved.setName(employeeDTO.getName());
                        employeeSaved.setEmail(employeeDTO.getEmail());
                        employeeSaved.setAddress(employeeDTO.getAddress());
                        employeeSaved.setCity(employeeDTO.getCity());
                        employeeSaved.setEmployeeType(employeeDTO.getEmployeeType());
                        employeeSaved.setSeniorityCompany(employeeDTO.getSeniorityCompany());
                        employeeSaved.setPhoneNumber(employeeDTO.getPhoneNumber());

                        return employeeRepository.save(employeeSaved);
                    });
            }
        }

    public String delete (Long id) {
        Optional<Employee> EmployeeDb = employeeRepository.findById(id);
        if (EmployeeDb.isPresent()) {
            employeeRepository.deleteById(id);
            return "message : The employee with ID " + id + " was successfully removed";
        }
        throw new DoesNotExistEntityException("Error : The employee with ID " + id + " does not exist");
    }

    public Employee getEmployeeById (Long id){

        Optional<Employee> employeeDb = employeeRepository.findById(id);

        if (employeeDb.isEmpty())
            throw new DoesNotExistEntityException("Error : The employee with ID " + id + " does not exist");
        else
            return employeeDb.get();
    }
    private boolean isCorrectEmployeeType (EmployeeDTO employeeDTO) {
        return employeeDTO.getEmployeeType().equalsIgnoreCase(EmployeeType.COORDINATOR.getNameSpanish()) || employeeDTO.getEmployeeType().equalsIgnoreCase(EmployeeType.DISTRIBUTOR.getNameSpanish()) || employeeDTO.getEmployeeType().equalsIgnoreCase(EmployeeType.DRIVER.getNameSpanish());
    }

    private Employee createEmployee (EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.getId(),employeeDTO.getName(),employeeDTO.getLastName(),employeeDTO.getPhoneNumber(),
                employeeDTO.getEmail(),employeeDTO.getAddress(), employeeDTO.getCity(),employeeDTO.getSeniorityCompany(),
                employeeDTO.getBloodType(),employeeDTO.getEmployeeType());
    }
}
