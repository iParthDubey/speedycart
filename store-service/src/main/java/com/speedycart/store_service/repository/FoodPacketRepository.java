package com.speedycart.store_service.repository;

import com.speedycart.store_service.model.FoodPacket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodPacketRepository extends JpaRepository<FoodPacket, Long>{
        Optional<FoodPacket> findFirstByReservedFalse();
}
