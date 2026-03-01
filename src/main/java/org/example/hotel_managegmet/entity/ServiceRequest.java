package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_requests")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    private ServiceRequestStatus status = ServiceRequestStatus.PENDING;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false)
    private HotelService service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;
}
