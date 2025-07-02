package com.speedycart.delivery_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery_agents")
@Getter
@Setter
public class DeliveryAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;

    @Column(name = "is_reserved")
    private boolean reserved;

    @Column(name ="order_id")
    private Long orderId;

}
