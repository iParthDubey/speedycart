package com.speedycart.common.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Assign request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPacketAssignRequestDto {
    private Long packetId;
    private Long orderId;
}
