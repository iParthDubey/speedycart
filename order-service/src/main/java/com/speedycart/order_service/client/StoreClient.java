package com.speedycart.order_service.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "store-service")
public interface StoreClient {

    @PostMapping("/api/store/reserve")
    ResponseEntity<String> reserveFood();

    @PostMapping("/api/store/assign")
    ResponseEntity<String> assignPacket(@RequestParam("packetId") Long packetId, @RequestParam("orderId") Long orderId);

    @PostMapping("/api/store/release")
    ResponseEntity<String> releasePacket(@RequestParam("packetId") Long packetId);

}
