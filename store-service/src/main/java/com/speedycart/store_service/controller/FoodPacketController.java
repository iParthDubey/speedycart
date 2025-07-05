package com.speedycart.store_service.controller;

import com.speedycart.store_service.service.FoodPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/store")
public class FoodPacketController {

    @Autowired
    private FoodPacketService service;

    @PostMapping("/reserve")
    public ResponseEntity<?> reservePacket() {
        return service.reservePacket()
                .<ResponseEntity<?>>map(packet -> ResponseEntity.ok(Map.of("packetId", packet.getId())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of("error", "No food packets available")));
    }


    @PostMapping("/assign")
    public ResponseEntity<?> assignPacket(@RequestParam Long packetId, @RequestParam Long orderId) {
        boolean assigned = service.assignPacket(packetId, orderId);
        return assigned
                ? ResponseEntity.ok("Packet assigned")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment failed");
    }

    @PostMapping("/release")
    public ResponseEntity<?> releasePacket(@RequestParam Long packetId) {
        boolean released = service.releasePacket(packetId);
        return released
                ? ResponseEntity.ok("Packet released")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Release failed");
    }

}
