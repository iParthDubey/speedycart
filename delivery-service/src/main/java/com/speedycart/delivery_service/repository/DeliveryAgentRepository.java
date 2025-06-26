package com.speedycart.delivery_service.repository;

import com.speedycart.delivery_service.model.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {

    // Custom query to find a delivery agent by order ID
    DeliveryAgent findByOrderId(Long orderId);

    // Custom query to find all reserved delivery agents
    List<DeliveryAgent> findByReservedTrue();

    // Custom query to find all unreserved delivery agents
    List<DeliveryAgent> findByReservedFalse();

    // Custom query to find a delivery agent by name
    Optional<DeliveryAgent> findFirstByReservedFalse();
}
