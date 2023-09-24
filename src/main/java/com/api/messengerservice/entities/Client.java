package com.api.messengerservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

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
    @Email
    private String email;
    @Column (nullable = false)
    private String address;
    @Column (nullable = false)
    private String city;

    public Client() {
    }

    public Client(Long id, String name, String lastName, String phoneNumber, String email, String address, String city) {
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
    }

    public boolean isValidId (Long id) {
        long minValue = 0L;
        long maxValue = 9999999999L;
        return id > minValue && id <= maxValue;
    }
}
