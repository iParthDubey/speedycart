package com.speedycart.order_service.client;

import com.speedycart.common.dto.delivery.DeliveryAgentAssignRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    @PostMapping("api/agents/reserve")
    ResponseEntity<Map<String, Object>> reserveDeliveryAgent();

    @PostMapping("/api/agents/assign")
    ResponseEntity<String> assignDeliveryAgent(@RequestBody DeliveryAgentAssignRequestDto agentAssignRequestDto);

    @PostMapping("/api/agents/release")
    ResponseEntity<String> releaseDeliveryAgent(@RequestParam("agentId") Long agentId);

}
