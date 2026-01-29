package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer roomNumber;
    private Integer floor;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private String viewType;
    private Boolean isSmoking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomtype_id")
    private RoomType roomType;
}
