package com.api.messengerservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table (name = "shipment")
public class Shipment {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column
    @NotEmpty
    private String originCity;
    @Column
    @NotEmpty
    private String destinationCity;
    @Column
    @NotEmpty
    private String destinationAddress;
    @Column
    @NotEmpty
    private String namePersonReceives;
    @Column
    @NotEmpty
    private String phonePersonReceives;
    @Column
    private LocalTime deliveryTime;
    @Column
    private DeliveryStatus deliveryStatus;

    @Column
    private double shipmentPrice;

    @OneToOne(mappedBy = "shipment", cascade = CascadeType.ALL)
    private Package aPackage;


}
