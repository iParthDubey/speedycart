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
    @Mapping(source = "id", target = "agentId")
    @Mapping(target = "status", constant = "reserved")
    DeliveryAgentReserveResponseDto toDto(DeliveryAgent agent);

    // DTO → Entity (used during save, assign etc.)
    DeliveryAgent toEntity(DeliveryAgentAssignRequestDto dto);
}
