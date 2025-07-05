package com.speedycart.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Reserve response
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentReserveResponseDto {

    private Long agentId;

    private String status; // "reserved" / "unavailable"
}

