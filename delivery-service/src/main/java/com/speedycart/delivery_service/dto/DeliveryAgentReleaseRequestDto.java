package com.speedycart.delivery_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Release request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentReleaseRequestDto {

    @NotNull(message = "agentId must not be null")
    private Long agentId;
}

