package com.api.messengerservice.entities;

import com.api.messengerservice.utils.DeliveryStatus;
import com.api.messengerservice.utils.PackageType;
import jakarta.persistence.*;
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
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;
    @Column (nullable = false,length = 20)
    private String originCity;
    @Column (nullable = false,length = 20)
    private String destinationCity;
    @Column (nullable = false,length = 50)
    private String destinationAddress;
    @Column (nullable = false,length = 50)
    private String namePersonReceives;
    @Column (nullable = false,length = 15)
    private String phonePersonReceives;
    @Column (nullable = false)
    private LocalTime deliveryTime;
    @Column (nullable = false,length = 10)
    private String deliveryStatus;
    @Column (nullable = false)
    private double shipmentPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id",nullable = false)
    private Package aPackage;

    public Shipment() {

    }

    public Shipment(Client client, String originCity, String destinationCity, String destinationAddress, String namePersonReceives, String phonePersonReceives, Package aPackage) {
        this.client = client;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.namePersonReceives = namePersonReceives;
        this.phonePersonReceives = phonePersonReceives;
        this.deliveryTime = LocalTime.now();
        this.deliveryStatus= DeliveryStatus.RECIBED.getNameSpanish();
        this.aPackage = aPackage;
        this.shipmentPrice = getShipmentPrice(aPackage.getPackageType());
    }

    private double getShipmentPrice(String packageType){
        if (packageType.equalsIgnoreCase("Grande"))
            return PackageType.LARGE.getPrice();
        else if (packageType.equalsIgnoreCase("Liviano"))
            return PackageType.LIGHTWEIGHT.getPrice();
        else
            return PackageType.MEDIUM.getPrice();
    }
}
