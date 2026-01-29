package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestServiceId;

    private String quantity;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "service_name")
    private Service service;
}
