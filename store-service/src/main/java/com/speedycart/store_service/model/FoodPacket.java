package com.speedycart.store_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "food_packets")
@Getter
@Setter
public class FoodPacket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "is_reserved")
    private boolean reserved;

    @Column(name = "order_id")
    private Long orderId;

}
