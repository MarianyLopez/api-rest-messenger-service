package com.api.messengerservice.services;

import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.entities.EmployeeType;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.exceptions.InvalidEmployeeTypeException;
import com.api.messengerservice.exceptions.IsNotExistEntityException;
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

    public Employee create(Employee employee) throws InvalidEmployeeTypeException {

            if (employeeRepository.findById(employee.getId()).isPresent())
                throw new InvalidCreateEntityException("Error: The employee already exists");
            else if (!isCorrectEmployeeType(employee))
                throw new InvalidEmployeeTypeException("Error : The employee has not been created because the employee type is not valid");
            return employeeRepository.save(employee);

    }

    public Optional<Employee> update(Long id, Employee employee){

        Optional<Employee> employeeDb = employeeRepository.findById(id);

        if (employeeDb.isEmpty()){
            throw new IsNotExistEntityException("Error : The employee with ID " + id + " does not exist");
        }else {
           return employeeDb
                    .map(employeeSaved -> {
                        employeeSaved.setName(employee.getName());
                        employeeSaved.setEmail(employee.getEmail());
                        employeeSaved.setAddress(employee.getAddress());
                        employeeSaved.setCity(employee.getCity());
                        employeeSaved.setEmployeeType(employee.getEmployeeType());
                        employeeSaved.setSeniorityCompany(employee.getSeniorityCompany());
                        employeeSaved.setPhoneNumber(employee.getPhoneNumber());

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
        return "message: The employee with ID " + id + " does not exist";
    }

    public Optional<Employee> getEmployeeById (Long id){

        Optional<Employee> employeeDb = employeeRepository.findById(id);

        if (employeeDb.isEmpty())
            throw new IsNotExistEntityException("Error : The employee with ID " + id + " does not exist");
        else
            return employeeDb;
    }
    private boolean isCorrectEmployeeType (Employee employee) {
        return employee.getEmployeeType().equalsIgnoreCase(EmployeeType.COORDINATOR.getNameSpanish()) || employee.getEmployeeType().equalsIgnoreCase(EmployeeType.DISTRIBUTOR.getNameSpanish()) || employee.getEmployeeType().equalsIgnoreCase(EmployeeType.DRIVER.getNameSpanish());
    }
}
