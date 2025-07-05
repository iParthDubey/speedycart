package com.speedycart.delivery_service.contoller;

import com.speedycart.common.dto.delivery.DeliveryAgentAssignRequestDto;
import com.speedycart.common.dto.delivery.DeliveryAgentReserveResponseDto;
import com.speedycart.delivery_service.service.DeliveryAgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/agents")
public class DeliveryAgentController {

    @Autowired
    private DeliveryAgentService service;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveAgent() {
        DeliveryAgentReserveResponseDto agent = service.reserveAgent();
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignAgent(@Valid @RequestBody DeliveryAgentAssignRequestDto dto) {
        boolean assigned = service.assignAgent(dto);
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
