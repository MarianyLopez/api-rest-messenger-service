package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.EmployeeDTO;
import com.api.messengerservice.entities.Employee;
import com.api.messengerservice.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@SecurityRequirement(name = "BearerAuth")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Endpoint to create a employee", description = "In the body of the request it receives a Json with the employee data needed to create it." +
            "Remember that 'employeeType' field must be one of the following: 'Repartidor','Coordinador' or 'Conductor'")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created. Employee successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request. The employee already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null or employee type is not valid ")})
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDTO));
    }

    @Operation(summary = "Endpoint to update a employee", description = "Receives an ID by path and in the body of the request it receives a Json with the employee data needed to update it." +
            "Remember that 'employeeType' field must be one of the following: 'Repartidor','Coordinador' or 'Conductor'")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. Employee successfully updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The employee does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(id, employeeDTO).get());
    }

    @Operation(summary = "Endpoint to delete a employee", description = "Receives a Employee ID by path to update it")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted. Employee successfully removed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The employee does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.delete(id));
    }

    @Operation(summary = "Endpoint to get a employee by ID", description = "Receives a Employee ID by path to get it")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. Employee successfully received"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not Found. The employee does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null")})
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
    }
}
