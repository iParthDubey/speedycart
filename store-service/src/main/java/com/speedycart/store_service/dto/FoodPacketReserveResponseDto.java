package com.speedycart.store_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPacketReserveResponseDto {
    private Long packetId;
    private String status; // "reserved" / "unavailable"
}
