package com.speedycart.delivery_service.service;

import com.speedycart.delivery_service.dto.DeliveryAgentAssignRequestDto;
import com.speedycart.delivery_service.dto.DeliveryAgentReserveResponseDto;
import com.speedycart.delivery_service.entity.DeliveryAgent;
import com.speedycart.delivery_service.mapper.DeliveryAgentMapper;
import com.speedycart.delivery_service.repository.DeliveryAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryAgentService {

    @Autowired
    private DeliveryAgentRepository repository;

    @Autowired
    private DeliveryAgentMapper mapper;

    public DeliveryAgentReserveResponseDto reserveAgent(){
        Optional<DeliveryAgent> agent = repository.findFirstByReservedFalse();
        if (agent.isPresent()) {
            DeliveryAgent deliveryAgent = agent.get();
            deliveryAgent.setReserved(true);
            deliveryAgent.setOrderId(null); // reservation phase, so no order ID is set
            repository.save(deliveryAgent);
        }

        return mapper.toDto(agent.get());
    }

    public boolean assignAgent(DeliveryAgentAssignRequestDto requestDto) {
        Optional<DeliveryAgent> optionalAgent = repository.findById(requestDto.getAgentId());
        if (optionalAgent.isPresent()) {
            DeliveryAgent deliveryAgent = optionalAgent.get();
            deliveryAgent.setOrderId(requestDto.getOrderId());
            repository.save(deliveryAgent);
            return true;
        }
        return false;
    }

    public boolean releaseAgent(Long agentId) {
        Optional<DeliveryAgent> optionalAgent = repository.findById(agentId);
        if (optionalAgent.isPresent()) {
            DeliveryAgent deliveryAgent = optionalAgent.get();
            deliveryAgent.setReserved(false);
            deliveryAgent.setOrderId(null); // release the agent, so no order ID is set
            repository.save(deliveryAgent);
            return true;
        }
        return false;
    }
}
