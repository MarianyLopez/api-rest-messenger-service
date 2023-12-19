package com.api.messengerservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShipmentResponseDTO {
    private Long clientID;
    private String originCity;
    private String destinationCity;
    private String destinationAddress;
    private String namePersonReceives;
    private String phonePersonReceives;
    private double weight;
    private double declaredValue;
    private String deliveryStatus;
    private double shipmentPrice;
}
