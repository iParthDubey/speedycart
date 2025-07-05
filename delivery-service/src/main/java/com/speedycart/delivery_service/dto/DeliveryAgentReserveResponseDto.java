package com.speedycart.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

// Reserve response
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentReserveResponseDto {

    private Long agentId;

    private String status; // "reserved" / "unavailable"
}

