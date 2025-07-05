package com.speedycart.order_service.client;

import com.speedycart.common.dto.store.FoodPacketAssignRequestDto;
import com.speedycart.common.dto.store.FoodPacketReleaseRequestDto;
import com.speedycart.common.dto.store.FoodPacketReserveResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "store-service")
public interface StoreClient {

    @PostMapping("/api/store/reserve")
    ResponseEntity<FoodPacketReserveResponseDto> reserveFood();

    @PostMapping("/api/store/assign")
    ResponseEntity<String> assignPacket(@RequestBody FoodPacketAssignRequestDto foodPacketAssignRequestDto);

    @PostMapping("/api/store/release")
    ResponseEntity<String> releasePacket(@RequestBody FoodPacketReleaseRequestDto foodPacketReleaseRequestDto);

}
