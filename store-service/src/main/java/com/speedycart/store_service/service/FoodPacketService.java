package com.speedycart.store_service.service;

import com.speedycart.store_service.model.FoodPacket;
import com.speedycart.store_service.repository.FoodPacketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodPacketService {

    @Autowired
    private FoodPacketRepository repository;

    public Optional<FoodPacket> reservePacket() {
        Optional<FoodPacket> packetOpt = repository.findFirstByReservedFalse();
        if (packetOpt.isPresent()) {
            FoodPacket packet = packetOpt.get();
            packet.setReserved(true);
            packet.setOrderId(null);
            repository.save(packet);
        }
        return packetOpt;
    }

    public boolean assignPacket(Long packetId, Long orderId) {
        return repository.findById(packetId).map(packet -> {
            if (packet.isReserved()) {
                packet.setOrderId(orderId);
                repository.save(packet);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public boolean releasePacket(Long packetId) {
        return repository.findById(packetId).map(packet -> {
            packet.setReserved(false);
            packet.setOrderId(null);
            repository.save(packet);
            return true;
        }).orElse(false);
    }

}
