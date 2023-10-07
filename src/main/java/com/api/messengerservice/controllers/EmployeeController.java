package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.EmployeeDTO;
import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {


    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(id,employeeDTO).get());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable ("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
    }
}
