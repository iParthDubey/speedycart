package com.speedycart.order_service.service;


import com.speedycart.order_service.client.DeliveryClient;
import com.speedycart.order_service.client.StoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private DeliveryClient deliveryClient;

    public String placeOrder(Long orderId){

        // Phase 1: Prepare (Reserve)
        var foodPacketResponse = storeClient.reserveFood();
        if(!foodPacketResponse.getStatusCode().is2xxSuccessful()){
            return "Order Falied : Food Unavailable";
        }

        Long packetId = Long.valueOf(foodPacketResponse.getBody());

        var agentResponse = deliveryClient.reserveDeliveryAgent();
        if(!agentResponse.getStatusCode().is2xxSuccessful()){
            storeClient.releasePacket(packetId);
            return "Oder Failed : No Delivery Agents";
        }
        Long agentId= ((Number) agentResponse.getBody()).longValue();

        // Phase 2: Commit (Assign)
        ResponseEntity<String> foodAssigned = storeClient.assignPacket(packetId, orderId);
        ResponseEntity<String> agentAssigned = deliveryClient.assignDeliveryAgent(agentId, orderId);

        if (foodAssigned.getStatusCode().is2xxSuccessful() && agentAssigned.getStatusCode().is2xxSuccessful()) {
            return "Order Placed Successfully";
        }

        // Rollback in case of partial commit failure
        storeClient.releasePacket(packetId);
        deliveryClient.releaseDeliveryAgent(agentId);
        return "Order Failed: Commit Phase Error";
    }



}
