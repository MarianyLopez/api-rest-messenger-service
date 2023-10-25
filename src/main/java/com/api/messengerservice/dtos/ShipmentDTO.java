package com.api.messengerservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShipmentDTO {
    @NotEmpty
    private Long clientID;
    @NotEmpty
    private String originCity;
    @NotEmpty
    private String destinationCity;
    @NotEmpty
    private String destinationAddress;
    @NotEmpty
    private String namePersonReceives;
    @NotEmpty
    private String phonePersonReceives;
    @NotEmpty
    private double weight;
    @NotEmpty
    private double declaredValue;

    private String deliveryStatus;

    private double shipmentPrice;


}
