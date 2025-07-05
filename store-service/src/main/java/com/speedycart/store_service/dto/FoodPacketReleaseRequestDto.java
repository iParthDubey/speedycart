package com.speedycart.store_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Release request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPacketReleaseRequestDto {
    private Long packetId;
}

