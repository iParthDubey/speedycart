package com.speedycart.delivery_service.mapper;

import com.speedycart.delivery_service.dto.DeliveryAgentAssignRequestDto;
import com.speedycart.delivery_service.dto.DeliveryAgentReserveResponseDto;
import com.speedycart.delivery_service.entity.DeliveryAgent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface DeliveryAgentMapper {

    // Entity → DTO
    DeliveryAgentReserveResponseDto toDto(Optional<DeliveryAgent> agent);

    // DTO → Entity (used during save, assign etc.)
    @Mapping(source = "agentId", target = "id")
    @Mapping(source = "orderId", target = "orderId")
    DeliveryAgent toEntity(DeliveryAgentAssignRequestDto dto);
}
