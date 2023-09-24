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
    public ResponseEntity<Object> create(@RequestBody EmployeeDTO employeeDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDTO));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(id,employeeDTO));
        }catch (NullPointerException e){
            return new  ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Long id){
        return employeeService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById (@PathVariable ("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
        }catch (NullPointerException e){
            return new  ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
