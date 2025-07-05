package com.speedycart.order_service.service;


import com.speedycart.common.dto.delivery.DeliveryAgentAssignRequestDto;
import com.speedycart.common.dto.store.FoodPacketAssignRequestDto;
import com.speedycart.common.dto.store.FoodPacketReleaseRequestDto;
import com.speedycart.common.dto.store.FoodPacketReserveResponseDto;
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

        Long packetId = null;
        // Phase 1: Prepare (Reserve)
        ResponseEntity<FoodPacketReserveResponseDto> response = storeClient.reserveFood();
        if(!(response.getStatusCode().is2xxSuccessful() && response.getBody().getPacketId() !=null)){
            return "Order Failed : Food Unavailable";
        }else{
            packetId = response.getBody().getPacketId();
        }

        var agentResponse = deliveryClient.reserveDeliveryAgent();
        FoodPacketReleaseRequestDto foodPacketReleaseRequestDto = new FoodPacketReleaseRequestDto(packetId);
        if(!agentResponse.getStatusCode().is2xxSuccessful()){
            storeClient.releasePacket(foodPacketReleaseRequestDto);
            return "Oder Failed : No Delivery Agents";
        }
        Long agentId= Long.valueOf(agentResponse.getBody().get("agentId").toString());

        // Phase 2: Commit (Assign)
        FoodPacketAssignRequestDto foodPacketAssignRequestDto = new FoodPacketAssignRequestDto(packetId,orderId);

        ResponseEntity<String> foodAssigned = storeClient.assignPacket(foodPacketAssignRequestDto);
        DeliveryAgentAssignRequestDto agentAssignRequestDto = new DeliveryAgentAssignRequestDto(agentId,orderId);
        ResponseEntity<String> agentAssigned = deliveryClient.assignDeliveryAgent(agentAssignRequestDto);

        if (foodAssigned.getStatusCode().is2xxSuccessful() && agentAssigned.getStatusCode().is2xxSuccessful()) {
            return "Order Placed Successfully " + orderId;
        }

        // Rollback in case of partial commit failure
        storeClient.releasePacket(foodPacketReleaseRequestDto);
        deliveryClient.releaseDeliveryAgent(agentId);
        return "Order Failed: Commit Phase Error";
    }



}
