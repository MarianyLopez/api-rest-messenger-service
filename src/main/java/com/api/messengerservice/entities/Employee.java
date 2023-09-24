package com.api.messengerservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @Column
    private Long id;
    @Column (nullable = false)
    private String name;
    @Column (nullable = false)
    private String lastName;
    @Column (nullable = false)
    private String phoneNumber;
    @Column (nullable = false)
    private String email;
    @Column (nullable = false)
    private String address;
    @Column (nullable = false)
    private String city;
    @Column (nullable = false)
    private int seniorityCompany;
    @Column (nullable = false)
    private String bloodType;
    @Column (nullable = false)
    private String employeeType;

    public Employee() {
    }

    public Employee(Long id, String name, String lastName, String phoneNumber, String email, String address, String city, int seniorityCompany, String bloodType, String employeeType) {
        if (!isValidId(id)) {
            this.id = null;
        }else {
            this.id = id;
        }
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.city = city;
        this.seniorityCompany = seniorityCompany;
        this.bloodType = bloodType;
        this.employeeType = employeeType;
    }

    public boolean isValidId (Long id) {
        long minValue = 0L;
        long maxValue = 9999999999L;
        return id > minValue && id <= maxValue;
    }
}
