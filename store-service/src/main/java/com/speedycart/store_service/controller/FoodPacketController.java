package com.speedycart.store_service.controller;

import com.speedycart.store_service.dto.FoodPacketAssignRequestDto;
import com.speedycart.store_service.dto.FoodPacketReleaseRequestDto;
import com.speedycart.store_service.dto.FoodPacketReserveResponseDto;
import com.speedycart.store_service.service.FoodPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store")
public class FoodPacketController {

    @Autowired
    private FoodPacketService service;

    @PostMapping("/reserve")
    public ResponseEntity<FoodPacketReserveResponseDto> reservePacket() {
        return service.reservePacket()
                .map(foodPacket -> ResponseEntity.ok(
                        new FoodPacketReserveResponseDto(foodPacket.getId(), "reserved")
                )).orElseGet(
                        ()-> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body(new FoodPacketReserveResponseDto(null, "unavailable"))
                );
    }


    @PostMapping("/assign")
    public ResponseEntity<String> assignPacket(@RequestBody @Validated FoodPacketAssignRequestDto dto) {
        boolean assigned = service.assignPacket(dto.getPacketId(), dto.getOrderId());
        return assigned
                ? ResponseEntity.ok("Packet assigned")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment failed");
    }

    @PostMapping("/release")
    public ResponseEntity<String> releasePacket(@RequestBody @Validated FoodPacketReleaseRequestDto dto) {
        boolean released = service.releasePacket(dto.getPacketId());
        return released
                ? ResponseEntity.ok("Packet released")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Release failed");
    }


}
