package com.speedycart.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    @PostMapping("api/agents/reserve")
    ResponseEntity<Map<String, Object>> reserveDeliveryAgent();

    @PostMapping("/api/agents/assign")
    ResponseEntity<String> assignDeliveryAgent(@RequestParam("agentId") Long agentId, @RequestParam("orderId") Long orderId);

    @PostMapping("/api/agents/release")
    ResponseEntity<String> releaseDeliveryAgent(@RequestParam("agentId") Long agentId);

}
