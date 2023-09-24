package com.api.messengerservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShipmentMessageDTO {
    private String guideNumber;
    private String deliveryStatus;

}
