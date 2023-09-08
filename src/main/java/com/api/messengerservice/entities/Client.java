package com.api.messengerservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @Column
    @NotNull(message = "The id cannot be null")
    private Long id;
    @Column (nullable = false)
    @NotEmpty(message = "The name cannot be empty")
    private String name;
    @Column (nullable = false)
    @NotEmpty (message = "The last name cannot be empty")
    private String lastName;
    @Column (nullable = false)
    @NotEmpty (message = "The phone number cannot be empty")
    private String phoneNumber;
    @Column (nullable = false)
    @NotEmpty (message = "The email cannot be empty")
    @Email
    private String email;
    @Column (nullable = false)
    @NotEmpty (message = "The address cannot be empty")
    private String address;
    @Column (nullable = false)
    @NotEmpty (message = "The city cannot be empty")
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
