package com.speedycart.delivery_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Assign request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentAssignRequestDto {

    @NotNull(message = "agentId must not be null")
    private Long agentId;

    @NotNull(message = "orderId must not be null")
    private Long orderId;
}

