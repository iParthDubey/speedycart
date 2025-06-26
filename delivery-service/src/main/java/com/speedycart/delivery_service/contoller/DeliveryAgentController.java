package com.speedycart.delivery_service.contoller;

import com.speedycart.delivery_service.model.DeliveryAgent;
import com.speedycart.delivery_service.service.DeliveryAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/agents")
public class DeliveryAgentController {

    @Autowired
    private DeliveryAgentService service;

    @PostMapping("/reserve")
    public ResponseEntity<Object> reserveAgent() {
        Optional<DeliveryAgent> agent = service.reserveAgent();
        return agent.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(503).body(
                        Map.of("error", "No available agents found")
                ));
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignAgent(@RequestParam Long agentId, @RequestParam Long orderId) {
        boolean assigned = service.assignAgent(agentId, orderId);
        return assigned
                ? ResponseEntity.ok("Agent assigned")
                : ResponseEntity.status(400).body("Assignment failed");
    }

    @PostMapping("/release")
    public ResponseEntity<?> releaseAgent(@RequestParam Long agentId) {
        boolean released = service.releaseAgent(agentId);
        return released
                ? ResponseEntity.ok("Agent released")
                : ResponseEntity.status(400).body("Release failed");
    }




}
