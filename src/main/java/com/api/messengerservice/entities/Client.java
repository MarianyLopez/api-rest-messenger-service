package com.api.messengerservice.entities;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Hidden
@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @Column (nullable = false,length = 10)
    private Long id;
    @Column (nullable = false,length = 30)
    private String name;
    @Column (nullable = false,length = 30)
    private String lastName;
    @Column (nullable = false,length = 15)
    private String phoneNumber;
    @Column (nullable = false,length = 50)
    @Email
    private String email;
    @Column (nullable = false,length = 50)
    private String address;
    @Column (nullable = false,length = 20)
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
