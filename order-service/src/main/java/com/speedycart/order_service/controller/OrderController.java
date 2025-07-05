package com.speedycart.order_service.controller;


import com.speedycart.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final RestTemplate restTemplate;

    private final String STORE_SERVICE_URL = "http://localhost:8081/api/store";
    private final String DELIVERY_SERVICE_URL = "http://localhost:8082/api/agents";

    @Autowired
    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder() {

        //Step 1: Reserve a food packet
        ResponseEntity<Map> foodPacketReserveResponse = restTemplate.postForEntity(
                STORE_SERVICE_URL + "/reserve", null, Map.class);
        if(!foodPacketReserveResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(foodPacketReserveResponse.getStatusCode())
                    .body("Failed to reserve food packet: " + foodPacketReserveResponse.getBody());
        }

        Long packetId = Long.parseLong(foodPacketReserveResponse.getBody().get("packetId").toString());

        // Step 2: Reserve delivery agent
        ResponseEntity<Map> agentReserveResponse = restTemplate.postForEntity(
                DELIVERY_SERVICE_URL + "/reserve", null, Map.class);
        if(!agentReserveResponse.getStatusCode().is2xxSuccessful()) {
            // Release the food packet if agent reservation fails
            restTemplate.postForEntity(STORE_SERVICE_URL + "/release?packetId=" + packetId, null, Void.class);
            return ResponseEntity.status(agentReserveResponse.getStatusCode())
                    .body("Failed to reserve delivery agent: " + agentReserveResponse.getBody());
        }

        Long agentId = Long.parseLong(agentReserveResponse.getBody().get("id").toString());


        // Generate orderId for tracking
        Long orderId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // Step 3: Assign food packet to order
        ResponseEntity<String> foodPacketAssignResponse = restTemplate.postForEntity(
                STORE_SERVICE_URL + "/assign?packetId=" + packetId + "&orderId=" + orderId, null, String.class);
        if(!foodPacketReserveResponse.getStatusCode().is2xxSuccessful()){
            // Release the agent if food packet assignment fails
            restTemplate.postForEntity(DELIVERY_SERVICE_URL + "/release?agentId=" + agentId, null, Void.class);
            // Also release the food packet
            restTemplate.postForEntity(STORE_SERVICE_URL + "/release?packetId=" + packetId, null, Void.class);
            return ResponseEntity.status(foodPacketAssignResponse.getStatusCode())
                    .body("Failed to assign food packet: " + foodPacketAssignResponse.getBody());
        }

        // Step 4: Assign delivery agent
        UriComponentsBuilder assignAgentUrl = UriComponentsBuilder
                .fromHttpUrl(DELIVERY_SERVICE_URL + "/assign")
                .queryParam("agentId", agentId)
                .queryParam("orderId", orderId);

        ResponseEntity<String> agentAssignRes = restTemplate.postForEntity(assignAgentUrl.toUriString(), null, String.class);
        if (!agentAssignRes.getStatusCode().is2xxSuccessful()) {
            restTemplate.postForEntity(STORE_SERVICE_URL + "/release?packetId=" + packetId, null, Void.class);
            restTemplate.postForEntity(DELIVERY_SERVICE_URL + "/release?agentId=" + agentId, null, Void.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign delivery agent");
        }
        return ResponseEntity.ok("Order placed successfully. Order ID: " + orderId);

    }

}
